// Test that the flushing API is generated with --flush=api
// .options: flush=api cache=all
// .grammar: { A; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();
    Object x = a.fresh();

    testSame(x, a.fresh());

    a.flushCache();
    a.flushTreeCache();
    a.flushAttrAndCollectionCache();
    a.flushAttrCache();
    a.flushCollectionCache();

    // Caches were not flushed because the --flush=api option is used.
    testSame(x, a.fresh());
  }
}
