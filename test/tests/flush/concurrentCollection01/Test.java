// Test that collection attributes are flushed correctly
// when using concurrent-evaluation.
// See Issue #289
// .grammar: { A::= B*; B; }
// .options: concurrent=true |
import static runtime.Test.*;
import java.util.ArrayList;

public class Test {
  public static void main(String args[]) {
    A a = new A();
    a.addB(new B());
    testEqual(a.bs().size(), 1);

    // Cache is reset after copying (1).
    A a2 = a.treeCopyNoTransform();
    a2.addB(new B());
    testEqual(2, a2.bs().size());

    // Cache is reset after copying (2).
    A a3 = a.treeCopy();
    a3.addB(new B());
    testEqual(2, a3.bs().size());

    // Cache is reset after flushTreeCache.
    a.flushTreeCache();
    a.addB(new B());
    testEqual(2, a.bs().size());
  }
}
