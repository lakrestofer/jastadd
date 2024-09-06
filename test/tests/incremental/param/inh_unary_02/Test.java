// Simple correctness test for incremental inherited attribute (lookup pattern).
// This test checks correct update after removing list node.
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A(
        new List(
            new B("b1", "b2"),
            new B("b2", "b1")),
        new C("c"));

    B b1 = a.getB(0),
      b2 = a.getB(1);

    Value<String> v1 = a.a();
    testSame(v1, a.a());
    testEquals("c", v1.x);
    testSame(b2, b1.decl());
    testSame(b1, b2.decl());

    // Transform.
    a.getBList().removeChild(1);

    // Check.
    testSame(v1, a.a()); // Check that A.a() was not recomputed.
    testSame(null, b1.decl()); // Check that A.B[0].decl() was recomputed.
  }
}
