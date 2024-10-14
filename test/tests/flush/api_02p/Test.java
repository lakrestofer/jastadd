// Test that the flushing API is generated with --flush=api
// .options: flush=full cache=all
// .grammar: { A; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();
    Object x = a.fresh();

    testSame(x, a.fresh());

    a.flushCache();

    // Caches were flushed, a.fresh() is recomputed.
    testNotSame(x, a.fresh());

    testSame(a.fresh(), a.fresh());
  }
}
