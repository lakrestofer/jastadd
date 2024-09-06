// Incremental collection attribute on non-root node with siblings.
// .options: rewrite=cnta incremental=param
import java.util.*;
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A();
    a.addB(new B(new List(new C("b0_0"), new C("b0_1"))));
    a.addB(new B(new List(new C("b1_0"), new C("b1_1"))));

    Collection<C>
      b0 = a.getB(0).b(),
      b1 = a.getB(1).b();

    testThat(names(b0)).containsExactly("b0_0", "b0_1");
    testThat(names(b1)).containsExactly("b1_0", "b1_1");

    // Check that B.b() is not recomputed if there are no changes.
    testSame(a.getB(0).b(), a.getB(0).b());
    testSame(a.getB(1).b(), a.getB(1).b());

    // Only A.B[0].b() is affected, but A.B[1].b() is also recomputed.
    a.getB(0).addC(new C("b0_x"));
    testNotSame(b1, a.getB(1).b());
    testThat(names(a.getB(0).b())).containsExactly("b0_0", "b0_1", "b0_x");
    testThat(names(a.getB(1).b())).containsExactly("b1_0", "b1_1");

    b0 = a.getB(0).b();
    b1 = a.getB(1).b();

    // Only A.B[1].b() is now affected, but A.B[0].b() is also recomputed.
    a.getB(1).addC(new C("b1_x"));
    testNotSame(b0, a.getB(0).b());
    testThat(names(a.getB(0).b())).containsExactly("b0_0", "b0_1", "b0_x");
    testThat(names(a.getB(1).b())).containsExactly("b1_0", "b1_1", "b1_x");

    b0 = a.getB(0).b();
    b1 = a.getB(1).b();

    // Finally test adding new B to the B list. Check that all B.b() are recomputed.
    a.addB(new B());
    testNotSame(b0, a.getB(0).b());
    testNotSame(b1, a.getB(1).b());
    testThat(names(a.getB(0).b())).containsExactly("b0_0", "b0_1", "b0_x");
    testThat(names(a.getB(1).b())).containsExactly("b1_0", "b1_1", "b1_x");
  }

  static Collection<String> names(Iterable<C> cs) {
    ArrayList<String> nm = new ArrayList<String>();
    for (C c : cs) {
      nm.add(c.getName());
    }
    return nm;
  }
}
