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

import core.JastAddConfiguration;
import core.TestConfiguration;
import core.TestProperties;
import core.TestRunner;
import core.TestOptions;
import core.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;

import org.jastadd.tinytemplate.Template;
import org.jastadd.tinytemplate.TemplateContext;
import org.jastadd.tinytemplate.TemplateParser;
import org.jastadd.tinytemplate.TemplateParser.SyntaxError;
import org.jastadd.tinytemplate.TinyTemplate;

public class DocumentationContext extends TemplateContext {

  private static final String NL = System.getProperty("line.separator");
  private static final TestProperties jastAddProperties = new TestProperties();
  private static final String version;
  static {
    jastAddProperties.put("jastadd3", "false");
    jastAddProperties.put("options", "indent=tab");
    JastAddConfiguration jastadd = JastAddConfiguration.get(jastAddProperties);
    version = jastadd.getVersion();
  }

  private final Collection<TestConfiguration> testSet = new LinkedList<TestConfiguration>();

  /**
   * Runs all tests encountered during the documentation generation process.
   */
  public int runTests(PrintStream out, PrintStream err) throws IOException {
    boolean failed = false;
    try {
      for (TestConfiguration config: testSet) {
        TestRunner.runTest(config, jastAddProperties);
      }
    } catch (AssertionError e) {
      err.println("Test error encountered:");
      e.printStackTrace(err);
      failed = true;
    }
    if (!failed) {
      out.append("<small>All examples tested with " + version + ".</small>");
      return 0;
    } else {
      out.append("**Test errors encountered with " + version + ".**");
      return 1;
    }
  }

  @Override
  public Object evalVariable(String varName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object evalAttribute(String attrName) {
    return null;
  }

  @Override
  public String evalIndentation(int level) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void expand(TemplateContext tc, String templateName, PrintStream out) {
    throw new RuntimeException("Not supported");
  }

  @Override
  public void expand(TemplateContext tc, String templateName, PrintWriter out) {
    throw new RuntimeException("Not supported");
  }

  @Override
  public void expand(TemplateContext tc, String templateName, StringBuffer out) {
    throw new RuntimeException("Not supported");
  }

  @Override
  public void expand(TemplateContext tc, String templateName, StringBuilder out) {
    String testPath = "documentation/" + templateName;
    File testDir = new File(Util.TEST_ROOT, testPath);
    File description = new File(testDir, "description");
    TinyTemplate tt = new TinyTemplate();
    if (!description.isFile()) {
      System.out.println("file: " + description.getAbsolutePath());
      out.append("Error: could not open file " + description.getAbsolutePath());
      return;
    }
    try {
      TemplateParser parser = new TemplateParser(tt, new FileInputStream(description));
      Template template = parser.parseSingleTemplate();
      TestConfiguration baseConfig = new TestConfiguration(Util.TEST_ROOT, testPath, new TestOptions(""));
      template.expand(new TestContext(baseConfig, jastAddProperties), out);
      TestProperties testProperties = Util.getTestProperties(testDir);
      String optionsProperty = testProperties.getProperty("options", "");
      String options[] = optionsProperty.split("\\|", -1);
      int index = 1;
      for (String option: options) {
        TestOptions testOptions = new TestOptions(option.trim(), options.length > 1, index++);
        TestConfiguration config = new TestConfiguration(Util.TEST_ROOT, testPath, testOptions, testProperties);
        testSet.add(config);
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(1);
    } catch (SyntaxError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(1);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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

}
