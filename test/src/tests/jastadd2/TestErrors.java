/* Copyright (c) 2005-2015, Jesper Öqvist <jesper.oqvist@cs.lth.se>
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
package tests.jastadd2;

import static org.junit.Assert.fail;

import core.TestConfiguration;
import core.TestRunner;
import core.ParallelParameterized;
import core.TestProperties;
import core.Util;

import java.io.IOException;
import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import tests.Tests;

/**
 * Runs all tests that are currently failing.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
@RunWith(ParallelParameterized.class)
public class TestErrors {

  private static final TestProperties properties = new TestProperties();
  static {
    properties.put("jastadd3", "false");
    properties.put("jastadd.jar", "jastadd2.jar");
    properties.put("options", "indent=tab");

    properties.include(Tests.FAILING);
  }

  private final TestConfiguration unitTest;

  /**
   * Construct a new JastAdd test
   * @param unitTest
   */
  public TestErrors(TestConfiguration unitTest) throws IOException {
    this.unitTest = unitTest;
  }

  /**
   * Run the JastAdd test
   */
  @Test
  public void runTest() throws IOException {
    boolean caught = false;
    try {
      TestRunner.runTest(unitTest, properties);
    } catch (AssertionFailedError e) {
      // Okay - expected test error.
      caught = true;
    } catch (AssertionError e) {
      // Okay - expected test error.
      caught = true;
    } finally {
      if (!caught) {
        fail("Test unexpectedly passed! Perhaps it should not be in the FAILING test set. Hint: edit src/tests/Tests.java");

      }
    }
  }

  @SuppressWarnings("javadoc")
  @Parameters(name = "{0}")
  public static Iterable<Object[]> getTests() {
    return Util.getTests(properties);
  }
}
