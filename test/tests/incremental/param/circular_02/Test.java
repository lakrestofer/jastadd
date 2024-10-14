// Test that a circular attribute is recomputed after a dependency change.
// .options: rewrite=cnta incremental=param
// .grammar: { A ::= <Max:int>; }
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A(0);

    Value v1 = a.cv();
    testEquals(v1.x, 0);
    testSame(v1, a.cv());

    // Check that cv is recomputed after dependency change.
    a.setMax(0);
    Value v2 = a.cv();
    testEquals(v2.x, 0);
    testNotSame(v1, v2);
    testSame(v2, a.cv());

    // Check that cv is recomputed after dependency change.
    a.setMax(4);
    Value v3 = a.cv();
    testEquals(v3.x, 4);
    testNotSame(v1, v3);
    testNotSame(v2, v3);
    testSame(v3, a.cv());
  }
}
