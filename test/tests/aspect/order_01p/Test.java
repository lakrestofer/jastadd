// Test declaration order dependency for interface / inherited equation.
// grammar = {A ::= B; B;}
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b = new B();
    A a = new A(b);
    testThat(b.a()).isSameAs(a);
  }
}
