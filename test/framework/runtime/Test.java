/* Copyright (c) 2013-2016, The JastAdd Team
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
package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("javadoc")
public class Test {
  public static void testNull(Object o) {
    testSame(null, o);
  }

  public static void testNotNull(Object o) {
    testNotSame(null, o);
  }

  /**
   * Tests that two references are identical
   * @param expected
   * @param actual
   */
  public static void testSame(Object expected, Object actual) {
    testSame(null, expected, actual);
  }

  public static void testSame(String message, Object expected, Object actual) {
    if (expected != actual) {
      message = message != null ? message + " " : "";
      error("%sObjects are not identical. Expected: <%s>, was: <%s>", message, expected, actual);
    }
  }

  public static void testNotSame(Object unexpected, Object actual) {
    testNotSame(null, unexpected, actual);
  }

  public static void testNotSame(String message, Object unexpected, Object actual) {
    if (unexpected == actual) {
      message = message != null ? message + " " : "";
      error("%sObjects are identical when expected to be different: <%s>", message, actual);
    }
  }

  public static void testEqual(Object expected, Object actual) {
    testEquals("Values did not compare equal.", expected, actual);
  }

  public static void testEquals(Object expected, Object actual) {
    testEquals("Values did not compare equal.", expected, actual);
  }

  public static void testEqual(String message, Object expected, Object actual) {
    testEquals(message, expected, actual);
  }

  private static boolean isEqual(Object a, Object b) {
    return (a == null) ? (a == b) : a.equals(b);
  }

  public static void testEquals(String message, Object expected, Object actual) {
    if (!isEqual(expected, actual)) {
      error("%s Expected: <%s>, was: <%s>", message, expected, actual);
    }
  }

  public static void testNotEquals(String message, Object unexpected, Object actual) {
    if (isEqual(unexpected, actual)) {
      error("%s Unexpected value: <%s>", message, actual);
    }
  }

  public static void test(String message, boolean condition) {
    testTrue(message, condition);
  }

  public static void test(boolean condition) {
    testTrue(condition);
  }

  public static void testTrue(String message, boolean condition) {
    testEqual(message, true, condition);
  }

  public static void testTrue(boolean condition) {
    testTrue(null, condition);
  }

  public static void testFalse(String message, boolean condition) {
    testEqual(message, false, condition);
  }

  public static void testFalse(boolean condition) {
    testFalse(null, condition);
  }

  public static void fail(String message) {
    error(message);
  }

  public static void error(String message, Object... args) {
    System.err.print("Test failed. ");
    if (message != null) {
      System.err.format(message, args);
    }
    System.err.println();

    ArrayList<String> ignore = new ArrayList<String>();
    ignore.add(Test.class.getName());
    ignore.add("java.lang.Thread");
    for (StackTraceElement e: Thread.currentThread().getStackTrace()) {
      if (!ignore.contains(e.getClassName())) {
        System.err.println("- " + e);
      }
    }
  }

  public static TestSubject testThat(Object o) {
    return new TestSubject(o);
  }

  public static <T> IterableTestSubject<T> testThat(Iterable<T> o) {
    return new IterableTestSubject<T>(o);
  }

  public static class TestSubject {
    private final Object subject;

    public TestSubject(Object obj) {
      this.subject = obj;
    }

    public boolean equal(Object expected) {
      testEquals(expected, subject);
      return true;
    }

    public boolean notEqual(Object unexpected) {
      testNotEquals("Values compared equal.", unexpected, subject);
      return true;
    }

    public void isSame(Object expected) {
      testSame(expected, subject);
    }

    public void isSameAs(Object expected) {
      testSame(expected, subject);
    }

    public void isEqualTo(Object expected) {
      testEquals(expected, subject);
    }

    public void isNotNull() {
      testNotNull(subject);
    }
  }

  public static class IterableTestSubject<T> extends TestSubject {
    private final Iterable<T> subject;

    public IterableTestSubject(Iterable<T> iter) {
      super(iter);

      this.subject = iter;
    }

    /** Test that the subject is empty. */
    public void isEmpty() {
      if (subject.iterator().hasNext()) {
        error(toString() + " not empty when expected to be empty");
      }
    }

    public void hasSize(int size) {
      testEqual("Wrong collection size.", size, size());
    }

    private int size() {
      int size = 0;
      Iterator<T> iter = subject.iterator();
      while (iter.hasNext()) {
        iter.next();
        size += 1;
      }
      return size;
    }

    /**
     * Test that the subject contains the same elements as the expected
     * elements, independent of order.
     */
    public void containsExactly(Iterable<T> expected) {
      if (!isEqualCollection(subject, expected)) {
        error("Collections are not equal. Expected: %s, was: %s", expected, subject);
      }
    }

    /**
     * Test that the subject contains the same elements as the expected
     * elements, independent of order.
     */
    public void containsExactly(T... expected) {
      ArrayList<T> list = new ArrayList<T>();
      for (T item : expected) {
        list.add(item);
      }
      if (!isEqualCollection(subject, list)) {
        error("Collections are not equal. Expected: %s, was: %s", list, subject);
      }
    }

    // See http://llbit.se/?p=2009 for algorithm description.
    private static boolean isEqualCollection(Iterable<?> a, Iterable<?> b) {
      Map<Object, Integer> map = new java.util.HashMap<Object, Integer>();
      for (Object o : a) {
        Integer val = map.get(o);
        int count = (val == null) ? 0 : val;
        map.put(o, count + 1);
      }
      for (Object o : b) {
        Integer val = map.get(o);
        int count;
        if (val != null) {
          count = val;
          if (count == 0) {
            return false;
          }
        } else {
          return false;
        }
        map.put(o, count - 1);
      }
      for (Integer count : map.values()) {
        if (count != 0) {
          return false;
        }
      }
      return true;
    }

    @Override public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      Iterator<T> iter = subject.iterator();
      while (iter.hasNext()) {
        if (sb.length() > 1) {
          sb.append(", ");
        }
        sb.append("" + iter.next());
      }
      return sb.append("]").toString();
    }
  }
}

