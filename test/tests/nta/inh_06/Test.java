// Test that NTA children are not evaluated during NTA construction. This test
// uses an inherited attribute in NTA construction. If NTAs are evaluated in
// inherited attribute evaluation then this will cause unbounded recursion and
// a StackOverflowException.
// .result=EXEC_PASS
// .grammar: { R ::= A*; A ::= /B0:B/ /B1:B/ /B2:B*/ /[B3:B]/; B ::= <Value:Integer>;}
// .options: rewrite=regular | rewrite=cnta | rewrite=none
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    R root = new R(new List<A>(new A(), new A(), new A()));

    testEqual(0, root.getA(0).getB0().getValue());
    testEqual(0, root.getA(0).getB1().getValue());
    testEqual(0, root.getA(0).getB2(0).getValue());
    testEqual(0, root.getA(0).getB3().getValue());

    testEqual(0, root.getA(1).getB0().getValue());
    testEqual(1, root.getA(1).getB1().getValue());
    testEqual(1, root.getA(1).getB2(0).getValue());
    testEqual(1, root.getA(1).getB3().getValue());

    testEqual(0, root.getA(2).getB0().getValue());
    testEqual(2, root.getA(2).getB1().getValue());
    testEqual(2, root.getA(2).getB2(0).getValue());
    testEqual(2, root.getA(2).getB3().getValue());

    testEqual(0, root.getA(0).getB0().value());
    testEqual(-1, root.getA(0).getB1().value());
    testEqual(-2, root.getA(0).getB2(0).value());
    testEqual(-3, root.getA(0).getB3().value());

    testEqual(1, root.getA(1).getB0().value());
    testEqual(-1, root.getA(1).getB1().value());
    testEqual(-2, root.getA(1).getB2(0).value());
    testEqual(-3, root.getA(1).getB3().value());

    testEqual(2, root.getA(2).getB0().value());
    testEqual(-1, root.getA(2).getB1().value());
    testEqual(-2, root.getA(2).getB2(0).value());
    testEqual(-3, root.getA(2).getB3().value());
  }
}
