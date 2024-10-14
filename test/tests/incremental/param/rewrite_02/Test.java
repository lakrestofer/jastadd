// Simple test that a rewrite reacts to dependency change.
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A(new B(), "b", new D());

    B b_nt = a.getBNoTransform();
    testFalse(b_nt instanceof C);

    // A.B is rewritten to a C because A.Name == "b".
    testTrue(a.getB() instanceof C);

    // Create dependency to rewritten child from sibling.
    testSame(a.getB(), a.getD().sibling());

    // Transform.
    a.setName("not b");

    // Check that D.sibling() is recomputed after flushing the rewrite.
    testSame(b_nt, a.getD().sibling());
  }
}
