/* Copyright (c) 2005-2021, Jesper Öqvist <jesper.oqvist@cs.lth.se>
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
package tests;

/**
 * Default test sets - defines which tests should be run.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public interface Tests {
  /**
   * Tests expected to fail.
   */
  String[] FAILING = {
    "copy/treeCopyNoTransform02",
    "nta/inh02",
    "nta/inh04",
    "rewrite/demo_java_names01",
    "refine/classdecl_01p",
    "weaving/nested_class_03p",
    "itd/class03", // Test for not-yet-implemented requested feature.
    "itd/class04", // Test for not-yet-implemented requested feature.
    "ast/list_08",
    "nta/get_notransform_01", // Reverted bugfix (https://bitbucket.org/jastadd/jastadd2/issues/215/getxnotransform-for-ntas).
    "nta/get_notransform_02", // Reverted bugfix (https://bitbucket.org/jastadd/jastadd2/issues/215/getxnotransform-for-ntas).
    "cache/declaration_03", // https://bitbucket.org/jastadd/jastadd2/issues/279/improve-cache-configurations-per-equation
    "incremental/region/Test175", // Varying debug output.
    "incremental/region/Test179", // Varying debug output.
    "coll/multi-target_03p", // https://bitbucket.org/jastadd/jastadd2/issues/326/multiple-contribution-to-different-targets
    "trace/coll04", // https://bitbucket.org/jastadd/jastadd2/issues/334/generated-tracing-code-for-collection
  };

  /**
   * Tests for unstable features, not yet released.
   * These tests are normally excluded.
   */
  String[] UNSTABLE = {
    "incremental/attr",
    "incremental/node",
    "copy/treeCopyNoTransform02",
    "copy/treeCopyNoTransform03"
  };
}
