// Test that singletons are used for uninitialized list/opt children.
// .options: emptyContainerSingletons
// .grammar: { A ::= B1:B* [B2:B]; B; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a1 = new A();
    A a2 = new A();
    A a3 = new A();
    testEqual(0, a1.getNumB1());
    testEqual(0, a2.getNumB1());
    testEqual(0, a3.getNumB1());
    testEqual(false, a1.hasB2());
    testEqual(false, a2.hasB2());
    testEqual(false, a3.hasB2());
    testSame(a1.getB1List(), a2.getB1List());
    testSame(a3.getB1List(), a2.getB1List());
    testSame(a1.getB2Opt(), a2.getB2Opt());
    testSame(a3.getB2Opt(), a2.getB2Opt());
    testSame(null, a1.getB1List().getParent());
    testSame(null, a2.getB2Opt().getParent());
    testSame(null, a3.getB1List().getParent());
  }
}
