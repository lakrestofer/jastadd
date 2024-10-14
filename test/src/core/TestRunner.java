/* Copyright (c) 2005-2015, The JastAdd Team
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Scanner;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * Utility methods for running JastAdd unit tests.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class TestRunner {

  private static int TEST_TIMEOUT = 5000;
  private static String SYS_LINE_SEP = System.getProperty("line.separator");
  private static String TEST_FRAMEWORK = "framework";

  static {
    // Set up test timeout.
    TEST_TIMEOUT = Integer.parseInt(System.getProperty("org.jastadd.testTimeout", "5000"));
  }

  /**
   * Run test with given JastAdd configuration.
   * @param config test case specific configuration
   * @param jastaddProperties global test configuration
   */
  public static void runTest(TestConfiguration config, Properties jastaddProperties)
      throws IOException {
    // Generate grammar.
    genExtraGrammar(config);

    // Generate JastAdd code.
    JastAddConfiguration jastadd = JastAddConfiguration.get(jastaddProperties);
    jastadd.invoke(config);

    Result expected = config.expected;

    if (expected == Result.JASTADD_PASSED || expected == Result.JASTADD_FAILED) {
      return;
    }

    if (config.expected == Result.JASTADD_ERR_OUTPUT) {
      compareJastAddErrOutput(config.tmpDir, config.testDir);
      return;
    }

    // Compile generated code.
    compileSources(config);

    if (expected == Result.COMPILE_PASSED || expected == Result.COMPILE_FAILED) {
      return;
    }

    // Execute the compiled code.
    String stdErr = executeCode(config);
    if (!stdErr.isEmpty()) {
      fail("Standard error not empty:\n" + stdErr);
    }

    if (expected == Result.EXEC_PASSED || expected == Result.EXEC_FAILED) {
      return;
    }

    // Compare the output with the expected output.
    compareOutput(config.tmpDir, config.testDir);
  }

  /**
   * Creates an extra .ast file from the grammar test property.
   */
  public static void genExtraGrammar(TestConfiguration config) throws IOException {
    String grammar = config.testProperties.getProperty("grammar", "").trim();
    if (grammar.startsWith("{") && grammar.endsWith("}")) {
      PrintWriter output = new PrintWriter(new File(config.tmpDir, "ExtraGrammarFile.ast"));
      output.append(grammar.substring(1, grammar.length() - 1));
      output.close();
    }
  }

  /**
   * Compare the error output from JastAdd
   */
  private static void compareJastAddErrOutput(File tmpDir, File testDir) {
    try {
      File expected = expectedJastAddErrorOutput(testDir);
      File actual = new File(tmpDir, "jastadd.err");
      assertEquals("Error output files differ",
          readFileToString(expected),
          readFileToString(actual));
    } catch (IOException e) {
      fail("IOException occurred while comparing JastAdd error output: " + e.getMessage());
    }
  }

  private static File expectedJastAddErrorOutput(File testDir) {
    boolean windows = System.getProperty("os.name").startsWith("Windows");
    if (windows) {
      // First try .win file.
      File file = new File(testDir, "jastadd.err.expected.win");
      if (file.isFile()) {
        return file;
      }
    }
    // Read default file:
    return new File(testDir, "jastadd.err.expected");
  }

  /**
   * Compare the generated output to the expected output
   */
  private static void compareOutput(File tmpDir, File testDir) {
    try {
      File expected = new File(testDir, "out.expected");
      File actual = new File(tmpDir, "out");
      assertEquals("Output files differ", readFileToString(expected),
          readFileToString(actual));

      expected = new File(testDir, "err.expected");
      actual = new File(tmpDir, "err");
      assertEquals("Error output files differ", readFileToString(expected),
          readFileToString(actual));
    } catch (IOException e) {
      fail("IOException occurred while comparing output: " + e.getMessage());
    }
  }

  /**
   * Reads an entire file to a string object.
   *
   * <p>If the file does not exist an empty string is returned.
   *
   * <p>The system dependent line separator char sequence is replaced by
   * the newline character.
   *
   * @param file
   * @return file contents as string
   * @throws FileNotFoundException
   */
  public static String readFileToString(File file) throws IOException {
    if (!file.isFile()) {
      return "";
    }

    FileInputStream in = new FileInputStream(file);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    while (true) {
      int size = in.read(buffer);
      if (size == -1) {
        break;
      }
      out.write(buffer, 0, size);
    }
    in.close();
    out.close();
    return normalizeText(out.toString("UTF-8"));
  }

  /**
   * Normalize line endings and replace back-slashes with slashes.
   * This is used to avoid insignificant platform differences from
   * altering test results.
   * @param text
   * @return normalized text string
   */
  private static String normalizeText(String text) {
    return text.replace(SYS_LINE_SEP, "\n").replace('\\', '/').trim();
  }

  /**
   * Run the compiled test program
   * @param config
   * @return The standard error content
   */
  private static String executeCode(TestConfiguration config) throws IOException {
    Properties props = config.testProperties;
    File tmpDir = config.tmpDir;

    ByteArrayOutputStream errors = new ByteArrayOutputStream();

    String classpath = tmpDir.getPath();
    if (props.containsKey("classpath")) {
      classpath += File.pathSeparator + props.getProperty("classpath");
    }

    try {
      OutputStream out = new FileOutputStream(new File(tmpDir, "out"));
      OutputStream errOut = new FileOutputStream(new File(tmpDir, "err"));
      ProcessThread pthread = new ProcessThread("java -classpath " + classpath + " Test",
          out, new MultiplexingOutputStream(errOut, errors));
      pthread.start();
      if (TEST_TIMEOUT > 0) {
        pthread.join(TEST_TIMEOUT);
      } else {
        pthread.join();
      }
      if (!pthread.processFinished) {
        pthread.killProcess();
        fail("Timed out while waiting for test execution (timeout = " + TEST_TIMEOUT + "ms)");
      }
      int exitValue = pthread.exitCode;
      if (exitValue == 0) {
        if (config.expected == Result.EXEC_FAILED) {
          fail("Code execution passed when expected to fail");
        }
        return errors.toString("UTF-8");
      }
    } catch (InterruptedException e) {
      fail("Interrupted while waiting for test process.");
    }

    if (config.expected != Result.EXEC_FAILED) {
      fail("Code execution failed when expected to pass:\n" + errors.toString("UTF-8"));
    }

    return errors.toString("UTF-8");
  }

  /**
   * Compile generated source files.
   * @param config
   */
  private static void compileSources(TestConfiguration config) {

    Collection<String> sourceFiles = collectSourceFiles(config.tmpDir);
    sourceFiles.addAll(collectSourceFiles(config.testDir));

    StringBuffer buff = new StringBuffer();
    buff.append("-d " + config.tmpDir.getPath());

    Properties props = config.testProperties;

    buff.append(" ");
    buff.append("-source " + config.sourceLevel + " ");
    buff.append("-classpath " + TEST_FRAMEWORK);
    if (props.containsKey("classpath")) {
      buff.append(File.pathSeparator);
      buff.append(props.getProperty("classpath"));
    }

    for (String sourceFile: sourceFiles) {
      buff.append(" ");
      buff.append(sourceFile);
    }

    String[] arguments = buff.toString().split(" ");
    StringBuffer errors = new StringBuffer();
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int exitValue = compiler.run(in, out, err, arguments);
    Scanner errScanner = new Scanner(new ByteArrayInputStream(err.toByteArray()));
    while (errScanner.hasNextLine()) {
      errors.append(errScanner.nextLine());
      errors.append("\n");
    }
    errScanner.close();

    if (exitValue == 0) {
      if (config.expected == Result.COMPILE_FAILED) {
        fail("Compilation passed when expected to fail");
      }
      return;
    }

    if (config.expected != Result.COMPILE_FAILED) {
      fail("Compilation failed when expected to pass:\n" + errors.toString());
    }
  }

  /**
   * Collect all source file names in the test directory.
   * @param dir
   * @return
   */
  private static Collection<String> collectSourceFiles(File dir) {
    Collection<String> sourceFiles = new LinkedList<String>();
    for (File file: dir.listFiles()) {
      if (!file.isDirectory() && file.getName().endsWith(".java")) {
        sourceFiles.add(file.getPath());
      } else if (file.isDirectory()) {
        sourceFiles.addAll(collectSourceFiles(file));
      }
    }
    return sourceFiles;
  }

}
