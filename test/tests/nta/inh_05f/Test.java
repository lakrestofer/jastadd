// Test null pointer exception when evaluating inherited attribute on
// NTA child of List subtype.
// .grammar: { AList : List; B ::= A*; A; }
// .result=EXEC_PASS
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    AList list = new AList();
    B b = new B(list);
    try {
      list.implicit().x();
      fail("Expected NullPointerException");
    } catch (NullPointerException e) {
    }
  }
}
