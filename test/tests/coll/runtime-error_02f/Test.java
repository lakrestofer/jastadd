// Evaluating a collection attribute in a subtree not connected to the collection root node
// causes a runtime exception. When the --debug flag is not supplied the exception is a
// null pointer exception.
// .result=OUTPUT_PASS
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new List().add(new B()).add(new B()));
    try {
      a.set();
    } catch (NullPointerException e) {
      return;
    }
    fail("expected null pointer exception");
  }
}
