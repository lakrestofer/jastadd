/* Copyright (c) 2014-2015, Jesper Öqvist <jesper.oqvist@cs.lth.se>
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
package doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.jastadd.tinytemplate.TemplateContext;

import core.JastAddConfiguration;
import core.TestConfiguration;
import core.TestOptions;
import core.TestRunner;
import core.Util;

public class TestContext extends TemplateContext {

  private static final String NL = System.getProperty("line.separator");
  private final TestConfiguration config;
  private final Properties jastaddProperties;
  private static final String REPO_URL = "https://bitbucket.org/jastadd/jastadd-test/src/master/";

  public TestContext(TestConfiguration config, Properties jastaddProperties) {
    this.config = config;
    this.jastaddProperties = jastaddProperties;
  }

  @Override
  public Object evalVariable(String varName) {
    return "unknown variable: " + varName;
  }

  @Override
  public Object evalAttribute(String attrName) {
    if (attrName.equals("TestOutput")) {
      try {
        return testOutput();
      } catch (IOException e) {
        System.err.format("Error during attribute evaluation of attribute %s: %s\n",
            attrName, e.getMessage());
        return "error";
      }
    } else if (attrName.equals("TestOutputAsDiagram")) {
      try {
        return testOutputDiagram();
      } catch (IOException e) {
        System.err.format("Error during attribute evaluation of attribute %s: %s\n",
            attrName, e.getMessage());
        return "error";
      }
    } else if (attrName.equals("AstClassDiagram")) {
      return astClassDiagram();
    } else {
      return "unknown attribute: " + attrName;
    }
  }

  @Override
  public String evalIndentation(int level) {
    throw new Error("not supported");
  }

  @Override
  public void expand(TemplateContext tc, String templateName, PrintStream out) {
    throw new Error("not supported");
  }

  @Override
  public void expand(TemplateContext tc, String templateName, PrintWriter out) {
    throw new Error("not supported");
  }

  @Override
  public void expand(TemplateContext tc, String templateName, StringBuffer out) {
    throw new Error("not supported");
  }

  @Override
  public void expand(TemplateContext tc, String templateName, StringBuilder out) {
    try {
      File file = new File(config.getDir(), templateName);
      String filepath = file.getPath();
      String filename = file.getName();
      StringBuilder buf = new StringBuilder();
      Scanner scanner = new Scanner(new FileInputStream(file));
      boolean hl = filename.endsWith(".java");
      out.append("[" + filename + "](" + REPO_URL + filepath + "?at=master):");
      out.append(NL);
      out.append(NL);
      if (hl) {
        out.append("~~~~{.java}");
        out.append(NL);
      }
      boolean haveStart = false;
      boolean inDoc = false;
      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        String trimmed = line.trim();
        if (line.trim().equals("// DOC START")) {
          if (!haveStart) {
            buf = out;
          }
          haveStart = true;
          inDoc = true;
        } else if (inDoc) {
          if (trimmed.equals("// DOC END")) {
            inDoc = false;
          } else {
            if (!hl) buf.append("    ");
            buf.append(line);
            buf.append(NL);
          }
        } else if (!haveStart) {
          if (!hl) buf.append("    ");
          buf.append(line);
          buf.append(NL);
        }
      }
      if (!haveStart) {
        out.append(buf.toString());
      }
      if (hl) {
        out.append("~~~~");
        out.append(NL);
      }
    } catch (IOException e) {
      System.err.println("failed to read test file: " + templateName);
    }
  }

  @Override
  public void bind(String varName, Object value) {
    // TODO Auto-generated method stub

  }

  @Override
  public void bind(String varName, boolean value) {
    // TODO Auto-generated method stub

  }

  @Override
  public void flushVariables() {
    // TODO Auto-generated method stub

  }

  private static final Pattern svg = Pattern.compile(".*(<svg.+</svg>).*",
      Pattern.DOTALL);

  /**
   * Evaluate the AST class diagram.
   * @return AST class diagram as SVG element
   */
  private String astClassDiagram() {
    TestConfiguration dotConfig = new TestConfiguration(Util.TEST_ROOT, config.getPath(), new TestOptions("dot"));
    JastAddConfiguration jastadd = JastAddConfiguration.get(jastaddProperties);
    jastadd.invoke(dotConfig);
    String outPath = new File(config.getTempDir(), "jastadd.out").getAbsolutePath();
    ProcessBuilder pb = new ProcessBuilder("dot", "-Tsvg", outPath);
    try {
      ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
      Process p = pb.start();
      InputStream in = p.getInputStream();
      int read;
      byte[] buf = new byte[300];
      while ((read = in.read(buf)) != -1) {
        output.write(buf, 0, read);
      }
      int exitCode = p.waitFor();
      if (exitCode != 0) {
        return "failed generate diagram";
      }
      String svgDoc = output.toString();
      Matcher matcher = svg.matcher(svgDoc);
      if (!matcher.matches()) {
        return svgDoc;
      }
      return matcher.group(1);
    } catch (IOException e) {
      e.printStackTrace();
      return "failed generate diagram";
    } catch (InterruptedException e) {
      e.printStackTrace();
      return "failed generate diagram";
    }
  }

  private String testOutputDiagram() throws IOException {
    TestRunner.runTest(config, jastaddProperties);
    String outPath = new File(config.getTempDir(), "out").getAbsolutePath();
    String command = "dot -Tsvg " + outPath;
    ProcessBuilder pb = new ProcessBuilder("dot", "-Tsvg", outPath);
    try {
      ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
      Process p = pb.start();
      InputStream in = p.getInputStream();
      int read;
      byte[] buf = new byte[300];
      while ((read = in.read(buf)) != -1) {
        output.write(buf, 0, read);
      }
      int exitCode = p.waitFor();
      if (exitCode != 0) {
        return "failed generate diagram: dot error (" + command + ")";
      }
      String svgDoc = output.toString();
      Matcher matcher = svg.matcher(svgDoc);
      if (!matcher.matches()) {
        return svgDoc;
      }
      return matcher.group(1);
    } catch (IOException e) {
      e.printStackTrace();
      return "failed generate diagram";
    } catch (InterruptedException e) {
      e.printStackTrace();
      return "failed generate diagram";
    }
  }

  private String testOutput() throws IOException {
    TestRunner.runTest(config, jastaddProperties);
    try {
      // TODO indentation
      File outFile = new File(config.getTempDir(), "out");
      return "<pre>" + TestRunner.readFileToString(outFile) + "</pre>";
    } catch (IOException e) {
      e.printStackTrace();
      return "failed read test output";
    }
  }

}
