// Using getXNoTransform() to access an NTA should compute the NTA, not
// return the uninitialized value of the NTA.
// This test checks List and Opt NTA components.
// .grammar: { A ::= /C1:B*/ /[C2:B]/; B; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a = new A();

    List<B> c1 = a.getC1ListNoTransform();
    testNotNull(c1);
    testSame(c1, a.getC1List());

    Opt<B> c2 = a.getC2OptNoTransform();
    testNotNull(c2);
    testSame(c2, a.getC2Opt());
  }
}
