// Simple test that a rewrite reacts to dependency change.
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A(new B("a"), "b");

    B b_nt = a.getBNoTransform();
    testFalse(b_nt instanceof C);

    // A.B is rewritten to a C because A.Name == "b".
    testTrue(a.getB() instanceof C);

    // Change propagation - flushes the A.B rewrite result.
    a.setName("not b");

    // Check.
    testSame(b_nt, a.getBNoTransform());
    testSame(b_nt, a.getB()); // No rewrite after transform.
  }
}
