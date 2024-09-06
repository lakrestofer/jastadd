/* Copyright (c) 2005-2017, The JastAdd Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package core;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A configuration to run a JastAdd instance.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class JastAddConfiguration {

  private final String path;
  private Class<?> klass = null;
  private boolean debugMode = false;

  /** Memoization map for used JastAdd classes. */
  private static final ConcurrentMap<String, Class<?>> classMap =
      new ConcurrentHashMap<String, Class<?>>();

  /**
   * Default compiler option set.
   */
  private final Collection<JastAddOption> defaultOptions = new LinkedList<JastAddOption>();

  /**
   * Create a new JastAdd configuration to run the JastAdd version
   * in the given jar file.
   * @param jarPath
   */
  public JastAddConfiguration(String jarPath) {
    this.path = jarPath;
  }

  /**
   * Invoke this JastAdd configuration on the given directory and fail
   * JUnit test when result does not match the expected result.
   * @param config Test configuration
   */
  public void invoke(TestConfiguration config) {
    File tmpDir = config.tmpDir;
    String testDirectory = config.testDir.getPath();
    Result expected = config.expected;

    if (config.options.hasOption("jjtree")) {
      invokeJJTree(tmpDir, testDirectory);
      invokeJavaCC(tmpDir, testDirectory);
    }

    ByteArrayOutputStream errors = new ByteArrayOutputStream(64);
    ByteArrayOutputStream output = new ByteArrayOutputStream(0);
    int exitValue = invokeJastAdd(config, errors, output);

    if (errors.size() > 0) {
      try {
        // Write errors to jastadd.err in tmpDir.
        PrintWriter errFile = new PrintWriter(new File(tmpDir, "jastadd.err"));
        errFile.append(errors.toString());
        errFile.close();
      } catch (IOException e) {
        System.err.println("Failed to write JastAdd error output file");
      }
    }

    if (output.size() > 0) {
      try {
        // Write output to jastadd.out in tmpDir.
        PrintWriter outFile = new PrintWriter(new File(tmpDir, "jastadd.out"));
        outFile.append(output.toString());
        outFile.close();
      } catch (IOException e) {
        System.err.println("Failed to write JastAdd output file");
      }
    }

    if (exitValue == 0) {
      // Code generation passed.
      if (expected == Result.JASTADD_FAILED) {
        fail("JastAdd code generation passed when expected to fail");
      }
    } else {
      // Code generation failed.
      if (expected != Result.JASTADD_FAILED && expected != Result.JASTADD_ERR_OUTPUT) {
        fail("JastAdd code generation failed when expected to pass:\n" +
          errors.toString());
      }
    }
  }

  /**
   * Invoke JastAdd. This is the bare-bones JastAdd invoking method, with no
   * error logging and result checking.
   * @param config
   * @param errors
   * @param output
   * @return exit code indicating success or failure
   */
  private int invokeJastAdd(TestConfiguration config, OutputStream errors, OutputStream output) {
    if (klass != null) {
      // Non-forked mode.
      try {
        Method main = klass.getMethod("compile", String[].class,
            PrintStream.class, PrintStream.class);
        int exitValue = (Integer) main.invoke(null,
            buildOptionArray(config),
            new PrintStream(output),
            new PrintStream(errors));
        return exitValue;
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    } else {
      // Forked mode.
      String command = "java ";
      if (debugMode) {
        command += "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 ";
      }
      command += "-jar " + path;
      String optionString = buildOptionString(config);
      String commandLine = command + " " + optionString;

      try {
        Process p = Runtime.getRuntime().exec(commandLine);
        Thread a = copyStream(p.getErrorStream(), errors);
        Thread b = copyStream(p.getInputStream(), output);
        a.join();
        b.join();
        int exitValue = p.waitFor();
        return exitValue;
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }

    return 1;
  }

  private Thread copyStream(final InputStream in, final OutputStream out) {
    // TODO(jesper): use ExecutorService to manage logging threads
    Thread thread = new Thread() {
      @Override
      public void run() {
        byte[] buf = new byte[300];
        int read;
        try {
          while (!isInterrupted() && (read = in.read(buf)) != -1) {
            out.write(buf, 0, read);
          }
        } catch (IOException e) {
        } finally {
          try {
            out.close();
          } catch (IOException e) {
          }
        }
      }
    };
    thread.start();
    return thread;
  }

  private void invokeJJTree(File tmpDir, String testDir) {
    String command = "java -classpath lib/javacc.jar org.javacc.jjtree.Main "
        + "-OUTPUT_DIRECTORY=" + tmpDir + " " + testDir + "/Test.jjt";
    StringBuffer errors = new StringBuffer();
    try {
      Process p = Runtime.getRuntime().exec(command);
      Scanner err = new Scanner(p.getErrorStream());
      while (err.hasNextLine()) {
        errors.append(err.nextLine());
        errors.append("\n");
      }
      err.close();
      int exitValue = p.waitFor();
      if (exitValue != 0) {
        fail("JJTree code generation failed!\n" + errors);
      }
    } catch (IOException e) {
      fail("JJTree code generation failed!\n" + e.getMessage());
    } catch (InterruptedException e) {
      fail("JJTree code generation failed!\n" + e.getMessage());
    }
  }

  private void invokeJavaCC(File tmpDir, String testDir) {
    String command = "java -classpath lib/javacc.jar org.javacc.parser.Main "
        + "-OUTPUT_DIRECTORY=" + tmpDir + " " + tmpDir + "/Test.jj";
    StringBuffer errors = new StringBuffer();
    try {
      Process p = Runtime.getRuntime().exec(command);
      Scanner err = new Scanner(p.getErrorStream());
      while (err.hasNextLine()) {
        errors.append(err.nextLine());
        errors.append("\n");
      }
      err.close();
      int exitValue = p.waitFor();
      if (exitValue != 0) {
        fail("JavaCC code generation failed!\n" + errors);
      }
    } catch (IOException e) {
      fail("JavaCC code generation failed!\n" + e.getMessage());
    } catch (InterruptedException e) {
      fail("JavaCC code generation failed!\n" + e.getMessage());
    }
  }

  private String[] buildOptionArray(TestConfiguration config) {
    List<String> args = new ArrayList<String>();

    // Set output directory.
    args.addAll(buildOutputDirOption(config.tmpDir.getPath()).toList());

    addExtraOptions(args);

    Set<JastAddOption> options = new HashSet<JastAddOption>();
    options.addAll(defaultOptions);
    options.addAll(optionsFromConfig(config.options));
    for (JastAddOption option: options) {
      args.addAll(option.toList());
    }

    String sources = config.testProperties.getProperty("sources", "");
    if (sources.isEmpty()) {
      // Find JastAdd source files in test directory:
      Iterable<String> sourceFiles = findSources(config);
      for (String path: sourceFiles) {
        args.add(path);
      }
    } else {
      // Use source list from test properties.
      for (String sourceFile: sources.split(",")) {
        sourceFile = sourceFile.replace('/', File.separatorChar);
        args.add(config.testDir.getPath() + File.separator + sourceFile);
      }
    }

    String[] array = new String[args.size()];
    for (int i = 0; i < args.size(); ++i) {
      array[i] = args.get(i);
    }
    return array;
  }

  private String buildOptionString(TestConfiguration config) {
    String[] args = buildOptionArray(config);
    StringBuilder buf = new StringBuilder();

    for (int i = 0; i < args.length; ++i) {
      if (i > 0) {
        buf.append(" ");
      }
      buf.append(args[i]);
    }

    return buf.toString();
  }

  protected void addExtraOptions(List<String> options) {
    // Place to add extra options if needed.
  }

  protected JastAddOption buildOutputDirOption(String path) {
    return new JastAddOption("o", path);
  }

  protected JastAddOption buildOption(String name) {
    return new JastAddOption(name);
  }

  protected JastAddOption buildOption(String name, String value) {
    return new JastAddOption(name, value);
  }

  private Iterable<String> findSources(TestConfiguration config) {
    List<String> sources = new ArrayList<String>();
    for (File file : config.testDir.listFiles()) {
      if (!file.isDirectory()) {
        String fileName = file.getName();
        if (fileName.endsWith(".jrag") || fileName.endsWith(".jadd") || fileName.endsWith(".ast")) {
          sources.add(file.getPath());
        }
      }
    }
    File extraGrammar = new File(config.tmpDir, "ExtraGrammarFile.ast");
    if (extraGrammar.isFile()) {
      sources.add(extraGrammar.getPath());
    }
    Collections.sort(sources);
    return sources;
  }

  /**
   * Get JastAdd options from configuration.
   * @param config test configuration
   * @return configured options
   */
  public Collection<JastAddOption> optionsFromConfig(TestOptions config) {
    Collection<JastAddOption> options = new LinkedList<JastAddOption>();
    for (String option: config.getOptions()) {
      if (option.startsWith("--")) {
        option = option.substring(2);
      }
      if (option.contains("=")) {
        String[] parts = option.split("=");
        options.add(buildOption(parts[0], parts[1]));
      } else {
        options.add(buildOption(option));
      }
    }
    return options;
  }

  /**
   * @param jastaddProperties
   * @return A JastAdd configuration with the options given in the properties
   */
  public static final JastAddConfiguration get(Properties jastaddProperties) {
    boolean fork = jastaddProperties.getProperty("fork", "true").equalsIgnoreCase("true");
    boolean debug = jastaddProperties.getProperty("debug_jastadd", "false").equalsIgnoreCase("true");
    String jarPath = jastaddProperties.getProperty("jastadd.jar", "");
    boolean jastadd3 = jastaddProperties.getProperty("jastadd3", "false").equalsIgnoreCase("true");
    String options = jastaddProperties.getProperty("options", "");

    JastAddConfiguration config;

    if (jastadd3) {
      config = new JastAdd3Configuration(jarPath);
    } else {
      config = new JastAddConfiguration(jarPath);
    }

    if (!fork && !debug) {
      if (!classMap.containsKey(jarPath)) {
        // Dynamically load JastAdd class from Jar file.
        try {
          File jarFile = new File(jarPath);
          URL jarUrl = jarFile.toURI().toURL();
          URLClassLoader classLoader =
              new URLClassLoader(new URL[] { jarUrl }, JastAddConfiguration.class.getClassLoader());
          Class<?> jastAdd;
          try {
            jastAdd = Class.forName("org.jastadd.JastAdd", true, classLoader);
          } catch (ClassNotFoundException e) {
            jastAdd = Class.forName("jastadd.JastAdd", true, classLoader);
          }
          // Memoize result:
          classMap.putIfAbsent(jarPath, jastAdd);
        } catch (MalformedURLException e) {
          throw new Error(e);
        } catch (ClassNotFoundException e) {
          throw new Error(e);
        } catch (SecurityException e) {
          throw new Error(e);
        }
      }

      // Set the JastAdd class for the configuration:
      config.klass = classMap.get(jarPath);
    }

    if (debug) {
      config.debugMode = true;
    }

    if (!options.isEmpty()) {
      // Default options.
      TestOptions opts = new TestOptions(jastaddProperties.getProperty("options"));
      config.defaultOptions.addAll(config.optionsFromConfig(opts));
    }

    return config;
  }

  /**
   * Get JastAdd version string
   * @return version string
   */
  public String getVersion() {
    // Forked mode.
    String command = "java -jar " + path;
    String optionString = "--version";
    String commandLine = command + " " + optionString;

    try {
      Process p = Runtime.getRuntime().exec(commandLine);
      Scanner out = new Scanner(p.getInputStream());
      String version = out.nextLine();
      out.close();
      int exitValue = p.waitFor();
      if (exitValue != 0) {
        return "error: could not get JastAdd version";
      }
      return version;
    } catch (IOException e) {
      return "error: could not get JastAdd version: " + e.getMessage();
    } catch (InterruptedException e) {
      return "error: could not get JastAdd version: " + e.getMessage();
    }
  }
}
