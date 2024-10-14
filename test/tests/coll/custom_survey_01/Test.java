// Test a custom survey block to add NTA contributions.
// .grammar: { A ::= B*; B; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    A a = new A(new List<B>(b1, b2));

    // Test that NTA node is found:
    testThat(a.bList()).containsExactly(b1, b2, a.implicitB());
  }
}
