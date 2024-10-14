// Arbitrary code can be used in the custom survey block.
// This test uses a toggle token on the A node type to determine which children
// are found by the collection attribute.
// .grammar: { A ::= <Toggle:Boolean> B*; B; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    A a = new A(true, new List<B>(b1, b2));
    testThat(a.bList()).containsExactly(a.implicitB());

    b1 = new B();
    b2 = new B();
    a = new A(false, new List<B>(b1, b2));
    testThat(a.bList()).containsExactly(b1, b2);
  }
}
