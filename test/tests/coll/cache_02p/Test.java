// Contribution targets are evaluated once per collection attribute, if any instance of
// the attribute is evaluated. Contribution targets should not be evaluated more than once.
//
// This version of the test has an equation on the collection root node.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new List().add(new B()).add(new B()));
    testEqual(2, a.childCounts().size());
    testEqual(1, a.targetEval);
    testTrue(a.childCounts().contains(0));
    testEqual(1, a.targetEval);
    testTrue(a.childCounts().contains(2));
    testEqual(1, a.targetEval);
  }
}
