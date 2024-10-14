// Test that empty singletons are replaced when parent node is mutated.
// .options: emptyContainerSingletons
// .grammar: { A ::= B1:B* [B2:B]; B; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a1 = new A();
    testSame(null, a1.getB1List().getParent());
    testSame(null, a1.getB2Opt().getParent());
    a1.addB1(new B());
    a1.setB2(new B());
    testSame(a1, a1.getB1List().getParent());
    testSame(a1, a1.getB2Opt().getParent());
    testEqual(1, a1.getNumB1());
    testEqual(true, a1.hasB2());
  }
}
