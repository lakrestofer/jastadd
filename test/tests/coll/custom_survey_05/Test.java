// Using a custom survey block does not hinder local contributions from the same node type.
// .grammar: { R ::= A; A ::= B* Thing:B; B; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    B b3 = new B();
    B b4 = new B();
    A a = new A(new List<B>(b1, b2, b3), b4);
    R r = new R(a);

    testThat(r.bList()).containsExactly(b4, a.implicitB());
  }
}
