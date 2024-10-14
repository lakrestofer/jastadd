// Evaluating a collection attribute in a subtree not connected to the collection root node
// causes a runtime exception.
// .options=debug
// .result=OUTPUT_PASS
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new List().add(new B()).add(new B()));
    try {
      a.set();
    } catch (RuntimeException e) {
      testEqual(
          "Trying to evaluate collection attribute A.set() in subtree not attached to main tree.",
          e.getMessage());
      return;
    }
    fail("expected runtime exception");
  }
}
