// Using @LazyCondition is dangerous, if the contribution target node can not
// always be computed and is nullable.
// See https://bitbucket.org/jastadd/jastadd2/issues/221/redundant-contribution-condition-test-in
// .grammar: { R ::= A X; A ::= X; X; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    X x0 = new X();
    X x1 = new X();
    R root = new R(new A(x0), x1);

    try {
      root.x();
      fail("Expected NullPointerException");
    } catch (NullPointerException e) {
      // PASS
    }
  }
}
