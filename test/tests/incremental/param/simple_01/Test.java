// Test memoization of nullary synthesized attributes without dependencies.
// .options: rewrite=cnta incremental=param
// .grammar: { A; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();

    // An unmemoized attribute is always recomputed.
    Object unmemo = a.unmemoized();
    testNotSame(unmemo, a.unmemoized());

    // A memoized attribute without dependencies is not recomputed.
    Object memo = a.memoized();
    testSame(memo, a.memoized());
  }
}
