// Test flushing of nullary NTA with dependency on token.
// .options: rewrite=cnta incremental=param
// .grammar: { A ::= <Name>; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(".");

    A a1 = a.x();
    testEquals("x.", a1.getName());
    testSame(a1, a.x()); // No change.

    // Check that A.x() is recomputed after change.
    a.setName(",");
    A a2 = a.x();
    testEquals("x,", a2.getName());
    testNotSame(a1, a2);
  }
}
