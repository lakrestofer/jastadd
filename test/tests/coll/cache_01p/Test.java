// Contribution targets are evaluated once per collection attribute, if any instance of
// the attribute is evaluated. Contribution targets should not be evaluated more than once.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    A a = new A(new List().add(b1).add(b2));

    testThat(a.b()).containsExactly(b1, b2);
    testEqual(1, b1.targetEval);

    testThat(a.b()).containsExactly(b1, b2);
    testEqual(1, b1.targetEval);
  }
}
