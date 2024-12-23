// Consider an interface with a synthesized attribute with a default equation.
// A node directly implementing the interface cannot override this equation.
// The result will be a double declaration. This behavior is questionable.
// Future development might allow such specifications. For the moment, you can
// program around this behavior by instead refining the equation in another
// aspect.
// .grammar: { A; B; }
// .result: JASTADD_PASS
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();
    B b = new B();
    testFalse(a.attr());
    testTrue(b.attr());
  }
}
