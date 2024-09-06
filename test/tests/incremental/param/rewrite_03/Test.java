// Test that nested rewrites react to a dependency change.
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A(new B(new D()), "a");

    testFalse(a.getBNoTransform() instanceof C);
    testFalse(a.getBNoTransform().getDNoTransform() instanceof E);

    // A.B is rewritten to a C because A.Name == "a".
    testTrue(a.getB() instanceof C);
    testTrue(a.getB().getDNoTransform() instanceof E);

    // Change propagation - flushes the A.B rewrite result.
    a.setName("not a");

    // A.B is not rewritten because A.Name != "a".
    testFalse(a.getB() instanceof C);
    testTrue(a.getB().getD() instanceof E);
  }
}
