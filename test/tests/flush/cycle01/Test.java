// Test that the cycle map is cleared when flushing parameterized attributes.
// https://bitbucket.org/jastadd/jastadd2/issues/276/parameterized-attributes-arent-fully
// .grammar: {X;}
// .result: EXEC_PASS
// .options: safeLazy lazyMaps visitCheck=false
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    X x = new X();

    testFalse(x.computedMapInitialized());

    x.py(3, 4);
    x.py(6, 8);
    x.py(5, 12);
    x.py(9, 12);
    x.py(8, 15);
    x.py(8, 15);

    testTrue(x.computedMapInitialized());
    x.flushCache();
    testFalse(x.computedMapInitialized());
  }
}

