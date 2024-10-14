// A single NTA chid can be searched for collection contributions using the
// short form of the custom survey declaration.
// .grammar: { R ::= A; A ::= B*; B; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    A a = new A(new List<B>(b1, b2));
    R r = new R(a);

    // Test that NTA contributions are found:
    testThat(r.bList()).containsExactly(b1, b2, a.implicitB1(), a.implicitB2());
  }
}
