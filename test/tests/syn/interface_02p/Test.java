// An interface can have a synthesized attribute with a default equation.
// The equation can be overridden in a subclass of an AST class implementing the interface.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B1 b1 = new B1();
    B2 b2 = new B2();
    testFalse(b1.attr());
    testTrue(b2.attr());
  }
}
