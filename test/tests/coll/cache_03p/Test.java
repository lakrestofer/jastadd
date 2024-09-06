// Contribution targets are evaluated once per collection attribute, if any instance of
// the attribute is evaluated. Contribution targets should not be evaluated more than once.
//
// This version of the test has an equation on the collection root node, but the root node
// does not declare the collection attribute.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    A a = new A(new List().add(b1).add(b2));

    testEqual(1, b1.parent().size());
    testEqual(1, a.targetEval);

    testSame(a, b1.parent().iterator().next());
    testEqual(1, a.targetEval);

    testEqual(1, b2.parent().size());
    testEqual(1, a.targetEval);

    testSame(a, b2.parent().iterator().next());
    testEqual(1, a.targetEval);
  }
}
