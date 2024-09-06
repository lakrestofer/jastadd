// List rewrites are not supported in cnta rewrite mode. This test checks
// that an error is reported if a list rewrite is used.
// .grammar: { A ::= B*; abstract B; C : B; D : B; E : B; }
// .options: rewrite=regular
// .result:  JASTADD_ERR_OUTPUT
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new List(new E(), new C(), new E(), new E(), new C()));
    testEqual(7, a.getNumB());
    testSame(E.class, a.getB(0).getClass());
    testSame(D.class, a.getB(1).getClass());
    testSame(D.class, a.getB(2).getClass());
    testSame(E.class, a.getB(3).getClass());
    testSame(E.class, a.getB(4).getClass());
    testSame(D.class, a.getB(5).getClass());
    testSame(D.class, a.getB(6).getClass());
  }
}
