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

import java.util.LinkedList;
import java.util.List;

/**
 * JastAdd3 command-line option
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class JastAdd3Option extends JastAddOption {

  private final String OPTION_PREFIX = "-";
  private final String VALUE_SEPARATOR = " ";

  /**
   * Basic command-line option
   * @param name
   */
  public JastAdd3Option(String name) {
    super(name);
  }

  /**
   * Key-value command-line option
   * @param name
   * @param value
   */
  public JastAdd3Option(String name, String value) {
    super(name, value);
  }

  @Override
  public String toString() {
    return OPTION_PREFIX + name + (hasValue ? VALUE_SEPARATOR + value : "");
  }

  /**
   * @return The option as a list of strings
   */
  @Override
  public List<String> toList() {
    List<String> list = new LinkedList<String>();
    list.add(OPTION_PREFIX + name);
    if (hasValue) {
      list.add(value);
    }
    return list;
  }
}
