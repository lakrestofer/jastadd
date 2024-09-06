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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Describes a particular set of options for a test.
 */
public class TestOptions {

  /**
   * JastAdd options from properties file
   */
  private final String options;

  /**
   * If the test case has more than one flag set
   */
  private final boolean multipleOptionSet;

  /**
   * Used to create different temp directories for each flag set
   */
  private final int flagSetIndex;

  private final Set<String> optionSet = new HashSet<String>();

  /**
   * @param options options for this particular test option set
   */
  public TestOptions(String options) {
    this(options, false, 1);
  }

  /**
   * @param options options for this particular test option set
   * @param hasMultipleOptions if this option set is part of multiple option sets
   * for the current test
   * @param flagSetIndex the index of this option set in the set of flags
   */
  public TestOptions(String options, boolean hasMultipleOptions, int flagSetIndex) {
    this.options = options;
    this.multipleOptionSet = hasMultipleOptions;
    this.flagSetIndex = flagSetIndex;
    buildSet();
  }

  private void buildSet() {
    String trimmed = options.trim();
    String[] opts;
    if (trimmed.isEmpty()) {
      opts = new String[0];
    } else {
      opts = trimmed.split("\\s+");
    }
    for (String opt: opts) {
      optionSet.add(opt);
    }
  }

  /**
   * Bulid new option set with some extra options.
   */
  public TestOptions addOptions(String extra) {
    extra = extra.trim();
    if (extra.isEmpty()) {
      return this;
    } else {
      return new TestOptions(options + " " + extra.trim(), multipleOptionSet, flagSetIndex);
    }
  }

  /**
   * @return Collection of option values.
   */
  public Collection<String> getOptions() {
    return new LinkedList<String>(optionSet);
  }

  /**
   * @return index in flag set
   */
  public int getFlagSetIndex() {
    return flagSetIndex;
  }

  /**
   * @return {@code true} if there are several flag sets.
   */
  public boolean inMultiOptionSet() {
    return multipleOptionSet;
  }

  @Override
  public String toString() {
    return "" + flagSetIndex + " - " + options;
  }

  /**
   * @param option
   * @return {@code true} if the given option is in this option set
   */
  public boolean hasOption(String option) {
    return optionSet.contains(option);
  }
}
