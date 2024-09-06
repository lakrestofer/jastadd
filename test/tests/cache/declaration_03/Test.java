// Test that a cache declaration can be used for an equation.
// See https://bitbucket.org/jastadd/jastadd2/issues/279/improve-cache-configurations-per-equation
// .grammar: { A; B : A; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    // The attribute B.x is not memoized.
    B b = new B();
    testSame(b.x(), b.x());

    // The attribute A.x is not memoized.
    A a = new A();
    testNotSame(a.x(), a.x());
  }
}
