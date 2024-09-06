// Fluid interface for AST construction.
// https://bitbucket.org/jastadd/jastadd2/pull-requests/10/fluent-interface-for-ast-nodes
// .grammar: { A ::= [B1:B] B2:B*; B ::= <ID>; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a = new A();
    a.getB1Opt().setChild(new B("0"), 0).setChild(new B("1"), 0);
    B b2_0 = a.getB2List().addChild(new B("l0")).add(new B("l1")).getChild(0);
    testEqual("1", a.getB1().getID());
    testSame(b2_0, a.getB2(0));
    testEqual("l0", a.getB2(0).getID());
    testEqual("l1", a.getB2(1).getID());
  }
}
