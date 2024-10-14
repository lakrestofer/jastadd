// Simple correctness test for incremental inherited attribute (lookup pattern).
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A(new List(
          new B("b1", "b2"),
          new B("b2", "b1")));

    B b1 = a.getB(0),
      b2 = a.getB(1);

    testSame(b2, b1.decl());
    testSame(b1, b2.decl());

    // Transform.
    b1.setName("B1");

    // Check.
    testSame(b2, b1.decl());
    testSame(null, b2.decl());

    // Transform.
    b2.setName("B2");

    // Check.
    testSame(null, b1.decl());
    testSame(null, b2.decl());
  }
}
