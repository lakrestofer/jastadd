// An interface can have a synthesized attribute with a default equation.
// This equation can be refined in an AST class directly implementing the interface.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();
    B b = new B();
    testTrue(a.attr());
    testFalse(b.attr());
  }
}
