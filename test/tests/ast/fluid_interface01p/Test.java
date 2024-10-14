// Fluid interface for AST construction.
// https://bitbucket.org/jastadd/jastadd2/pull-requests/10/fluent-interface-for-ast-nodes
// .grammar: { A ::= <ID> B [B1:B] B2:B*; B ::= <ID>; }
// .options: emptyContainerSingletons |
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a = new A()
        .setID("A")
        .setB(new B("0"))
        .setB1(new B("1"))
        .addB2(new B("2"))
        .addB2(new B("3"));
    testEqual("A", a.getID());
    testEqual("0", a.getB().getID());
    testEqual("1", a.getB1().getID());
    testEqual("2", a.getB2(0).getID());
    testEqual("3", a.getB2(1).getID());
  }
}
