/* Copyright (c) 2005-2021, The JastAdd Team
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

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.fail;

/**
 * Configuration for a single test - includes info about the test to run, the
 * test root directory, and JastAdd configuration for the test run.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class TestConfiguration {
  /**
   * The path from the test root to the unit test.
   **/
  private final String path;

  protected File testDir;
  protected TestOptions options;
  protected final TestProperties testProperties;
  protected final Result expected;
  protected final File tmpDir;
  protected final String sourceLevel;

  /**
   * @param testRoot
   * @param testPath
   * @param testOptions
   */
  public TestConfiguration(String testRoot, String testPath, TestOptions testOptions) {
    this(testRoot, testPath, testOptions, Util.getTestProperties(new File(testRoot, testPath)));
  }

  /**
   * @param testRoot
   * @param testPath
   * @param testOptions
   * @param testProperties
   */
  public TestConfiguration(String testRoot, String testPath, TestOptions testOptions,
      TestProperties testProperties) {
    path = testPath;
    options = testOptions;
    testDir = new File(testRoot, path);
    this.testProperties = testProperties;
    expected = getExpectedResult(testProperties);
    sourceLevel = getSourceLevel(testProperties);
    tmpDir = setupTemporaryDirectory(testDir, options);
  }

  private static Result getExpectedResult(Properties props) {
    if (!props.containsKey("result"))
      return Result.EXEC_PASSED;

    String result = props.getProperty("result");
    if (result.equals("JASTADD_PASSED") || result.equals("JASTADD_PASS")) {
      return Result.JASTADD_PASSED;
    } else if (result.equals("JASTADD_FAILED") || result.equals("JASTADD_FAIL")) {
      return Result.JASTADD_FAILED;
    } else if (result.equals("JASTADD_ERR_OUTPUT") || result.equals("JASTADD_ERR")) {
      return Result.JASTADD_ERR_OUTPUT;
    } else if (result.equals("COMPILE_PASSED") || result.equals("COMPILE_PASS")) {
      return Result.COMPILE_PASSED;
    } else if (result.equals("COMPILE_FAILED") || result.equals("COMPILE_FAIL")) {
      return Result.COMPILE_FAILED;
    } else if (result.equals("EXEC_PASSED") || result.equals("EXEC_PASS")) {
      return Result.EXEC_PASSED;
    } else if (result.equals("EXEC_FAILED") || result.equals("EXEC_FAIL")) {
      return Result.EXEC_FAILED;
    } else if (result.equals("OUTPUT_PASSED") || result.equals("OUTPUT_PASS")) {
      return Result.OUTPUT_PASSED;
    } else {
      fail("Unknown result option: " + result);
      return Result.OUTPUT_PASSED;
    }
  }

  /**
   * Returns the source level specified in the test properties, or 1.7 if
   * there was no source level specified.
   */
  private static String getSourceLevel(Properties props) {
    if (!props.containsKey("source")) {
      return "1.7";
    }
    return props.getProperty("source");
  }

  /**
   * Set up the temporary directory - create it if it does not exist
   * and clean it if it does already exist.
   * @param testDir The test directory
   * @param unitTest
   */
  private static File setupTemporaryDirectory(File testDir, TestOptions unitTest) {
    String tmpDirName;
    if (unitTest.inMultiOptionSet()) {
      tmpDirName = testDir.getPath() + "-" + unitTest.getFlagSetIndex();
    } else {
      tmpDirName = testDir.getPath();
    }

    if (tmpDirName.startsWith("tests")) {
      tmpDirName = tmpDirName.substring(6);
    }

    File tmpDir = new File("tmp" + File.separator + tmpDirName);

    if (!tmpDir.exists()) {
      // create directory with intermediate parent directories
      tmpDir.mkdirs();
    } else {
      // clean temporary directory
      cleanDirectory(tmpDir);
    }
    return tmpDir;
  }

  /**
   * Recursively remove all files and folders in a directory
   * @param dir The directory to nuke
   */
  private static void cleanDirectory(File dir) {
    for (File file: dir.listFiles()) {
      if (!file.isDirectory()) {
        file.delete();
      } else {
        cleanDirectory(file);
        file.delete();
      }
    }
  }

  /**
   * Add extra command-line options to this test configuration.
   */
  public void addOptions(String extra) {
    options = options.addOptions(extra);
  }

  @Override
  public String toString() {
    if (options.inMultiOptionSet()) {
      return path + " - " + options.toString();
    } else {
      return path;
    }
  }

  /**
   * @return The test path.
   */
  public String getPath() {
    return path;
  }

  /**
   * @return The test directory
   */
  public File getDir() {
    return testDir;
  }

  /**
   * @return The temporary directory for this test
   */
  public File getTempDir() {
    return tmpDir;
  }

  /**
   * @return options
   */
  public TestOptions getOptions() {
    return options;
  }

}
