/* Copyright (c) 2025, The JastAdd Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Lund University nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package tests.jastadd2.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import org.jastadd.ast.AST.InheritanceOrder;
import org.jastadd.ast.AST.InheritanceConstraintOrdering;


public class TestInterface {


  /**
   * Test that the most basic IHierarchy functionality works as intended
   */
  @Test
  public void testIHierarchySanity() {
    IHierarchy ih = new IHierarchy();
    ih.def("object");
    ih.def("printable");
    ih.def("number").extending("object");
    ih.def("int").extending("number", "printable");
    ih.def("short").extending("int");
    ih.def("long").extending("int", "printable");
    ih.def("string").extending("object", "printable");
    ih.link();

    assertTrue(ih.ty("number").isSubInterfaceOf(ih.ty("object")));
    assertTrue(ih.ty("int").isSubInterfaceOf(ih.ty("number")));
    assertTrue(ih.ty("int").isSubInterfaceOf(ih.ty("object")));
    assertTrue(ih.ty("string").isSubInterfaceOf(ih.ty("printable")));
    assertTrue(ih.ty("short").isSubInterfaceOf(ih.ty("object")));
    assertTrue(ih.ty("short").isSubInterfaceOf(ih.ty("printable")));

    assertFalse(ih.ty("number").isSubInterfaceOf(ih.ty("printable")));
    assertFalse(ih.ty("short").isSubInterfaceOf(ih.ty("short")));
    assertFalse(ih.ty("object").isSubInterfaceOf(ih.ty("object")));
  }


  /**
   * Test that IHierarchy works fine for testing direct inheritance cycles
   */
  @Test
  public void testIHierarchyDirectCycle() {
    IHierarchy ih = new IHierarchy();
    ih.def("object").extending("object");
    ih.link();

    assertTrue(ih.ty("object").isSubInterfaceOf(ih.ty("object")));
  }

  /**
   * Test that IHierarchy works fine for testing indirect inheritance cycles
   */
  @Test
  public void testIHierarchyIndirectCycle() {
    IHierarchy ih = new IHierarchy();
    IHierarchy.ISpec object, A, B, C, D;
    object = ih.def("object");
    A = ih.def("A").extending("object", "C");
    B = ih.def("B").extending("A");
    C = ih.def("C").extending("B");
    D = ih.def("D").extending("B");
    ih.link();

    assertTrue(A.isSubInterfaceOf(object));
    assertTrue(A.isSubInterfaceOf(A));
    assertTrue(A.isSubInterfaceOf(B));
    assertTrue(B.isSubInterfaceOf(A));
    assertTrue(D.isSubInterfaceOf(A));

    assertFalse(A.isSubInterfaceOf(D));
  }

  /**
   * Assert that an interface ordering solution is correct.
   *
   * @param exact Solution must be the exact solution specified in <tt>names<tt>
   *    (for cases where there are multiple solutions, specify <tt>false</tt> here)
   * @param <tt>iconstraints<tt> access to the solution
   * @param <tt>ih<tt> The subtype hierarchy
   * @param <tt>names<tt> The names that must appear in the solution
   */
  void
  assertOrder(boolean exact, InheritanceConstraintOrdering<IHierarchy.ISpec> iconstraints, IHierarchy ih, String ... names) {
    ArrayList<IHierarchy.ISpec> solution = iconstraints.getOrdered();
    ArrayList<IHierarchy.ISpec> expected = new ArrayList<IHierarchy.ISpec>();
    for (String n : names) {
      expected.add(ih.ty(n));
    }
    assertEquals(new HashSet<IHierarchy.ISpec>(expected),
                 new HashSet<IHierarchy.ISpec>(solution));
    for (int i = 0; i < solution.size(); ++i) {
      IHierarchy.ISpec elt = solution.get(i);
      for (int j = i + 1; j < solution.size(); ++j) {
        IHierarchy.ISpec laterElt = solution.get(j);
        assertFalse(laterElt.isSubInterfaceOf(elt));
      }
    }
    if (exact) {
      assertEquals(expected, solution);
    }
  }

  InheritanceConstraintOrdering<IHierarchy.ISpec>
  buildIConstraints(IHierarchy ih, String ... names) {
    ArrayList<IHierarchy.ISpec> elts = new ArrayList<IHierarchy.ISpec>();
    for (String n : names) {
      elts.add(ih.ty(n));
    }
    return new InheritanceConstraintOrdering<IHierarchy.ISpec>(elts);
  }

  @Test
  public void testInheritanceConstraintOrderingSimple() {
    IHierarchy ih = new IHierarchy();
    ih.def("object");
    ih.def("A").extending("object");
    ih.def("B").extending("A");
    ih.def("C").extending("B");
    ih.link();

    InheritanceConstraintOrdering<IHierarchy.ISpec> iconstraints =
      buildIConstraints(ih, "C", "A", "B", "C" );

    assertOrder(true, iconstraints, ih,
                "C", "B", "A");
  }

  @Test
  public void testInheritanceConstraintOrderingWithUnordered() {
    IHierarchy ih = new IHierarchy();
    ih.def("A").extending();
    ih.def("B").extending("A");
    ih.def("C").extending("B");

    ih.def("_any0");
    ih.def("_any1");
    ih.def("_any2");
    ih.link();

    InheritanceConstraintOrdering<IHierarchy.ISpec> iconstraints =
      buildIConstraints(ih, "C", "_any2", "A", "_any0", "_any1", "B", "_any2", "C");

    assertOrder(false, iconstraints, ih,
                "C", "B", "A", "_any0", "_any1", "_any2");
  }

  @Test
  public void testInheritanceConstraintOrderingWithTwoClusters() {
    IHierarchy ih = new IHierarchy();
    ih.def("A").extending();
    ih.def("B").extending("A");
    ih.def("C").extending("B");

    ih.def("X0").extending();
    ih.def("X1").extending();
    ih.def("Y").extending("X0", "X1");
    ih.def("Z0").extending("Y");
    ih.def("Z1").extending("Y");

    ih.def("_any0");
    ih.def("_any1");
    ih.def("_any2");
    ih.link();

    InheritanceConstraintOrdering<IHierarchy.ISpec> iconstraints =
      buildIConstraints(ih, "C", "_any2", "Z1", "A", "_any0", "X0", "Z0", "X1","_any1", "B", "_any2", "C", "Z1");

    assertOrder(false, iconstraints, ih,
                "C", "B", "A", "_any0", "_any1", "_any2", "X0", "X1", "Z0", "Z1");
  }


  /**
   * Inheritance order tree
   */
  static class IHierarchy {
    HashMap<String, ISpec> allSpecs = new HashMap<String, ISpec>();

    /**
     * sanity-check
     */
    public void link() {
      for (ISpec spec : allSpecs.values()) {
        spec.link();
      }
    }

    public ISpec ty(String name) {
      ISpec spec = allSpecs.get(name);
      if (spec == null) {
        throw new RuntimeException("Unknown ISpec: '" + name + "'");
      }
      return spec;
    }

    public ISpec def(String name) {
      if (allSpecs.containsKey(name)) {
        throw new RuntimeException("Dual definition of '"+name+"'");
      }
      ISpec s = new ISpec(name);
      return s;
    }

    /**
     * Mock interface declaration
     */
    class ISpec implements InheritanceOrder<ISpec> {
      private String name;
      private String[] supersNames = new String[0];
      ISpec[] supers;

      ISpec(String name) {
        this.name = name;
        IHierarchy.this.allSpecs.put(name, this);
      }

      public ISpec
      extending(String ... supers) {
        this.supersNames = supers;
        return this;
      }

      /**
       * Translate names of parent mock interfaces to mock interfaces
       */
      void link() {
        this.supers = new ISpec[this.supersNames.length];
        int i = 0;
        for (String superName : this.supersNames) {
          ISpec superSpec = IHierarchy.this.allSpecs.get(superName);
          assertNotNull(superSpec);
          this.supers[i++] = superSpec;
        }
      }

      @Override
      public boolean isSubInterfaceOf(ISpec other) {
        // Strict superinterface only
        for (ISpec parent : this.supers) {
          if (parent.isSuperOrEqual(other, new HashSet<String>())) {
            return true;
          }
        }
        return false;
      }

      /**
       * Check super-or-equal property while blocking infinite recursion
       */
      private boolean isSuperOrEqual(ISpec other, HashSet<String> recursionGuard) {
        if (this == other) {
          return true;
        }
        recursionGuard.add(this.name());

        for (ISpec parent : this.supers) {
          if (!recursionGuard.contains(parent.name())) {
            if (parent.isSuperOrEqual(other, recursionGuard)) {
              return true;
            }
          }
        }
        return false;
      }

      @Override
      public String name() {
        return this.name;
      }

      // @Override
      // public int hashCode() {
      //   throw new UnsupportedOperationException();
      // }

      public int compareTo(Object obj) {
        throw new UnsupportedOperationException();
      }

      // public boolean equals(Object obj) {
      //   throw new UnsupportedOperationException();
      // }

      @Override
      public String toString() {
        return this.name;
      }
    }
  }
}
