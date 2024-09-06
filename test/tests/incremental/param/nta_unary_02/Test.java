// Test flushing of unary NTA with dependency on children.
// .options: rewrite=cnta incremental=param
// .grammar: { A ::= B*; B ::= <ID>; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    for (int i = 0; i < 3; ++i) {
      // Modify A.B[i].
      A a = new A(new List<B>(
            new B("Burt"),
            new B("Bort"),
            new B("Bert")));

      B[] b1 = { a.b(0), a.b(1), a.b(2) };
      testEquals("A_0_Burt", b1[0].getID());
      testEquals("A_1_Bort", b1[1].getID());
      testEquals("A_2_Bert", b1[2].getID());
      for (int k = 0; k < 3; ++k) {
        testSame(b1[k], a.b(k));
      }

      // Check that only A.b(i) is affected by changing A.B[i].
      a.getBList().setChild(new B("Blort"), i);
      B[] b2 = { a.b(0), a.b(1), a.b(2) };
      testEquals("A_" + i + "_Blort", b2[i].getID());
      testNotSame(b1[i], b2[i]);
      for (int k = 0; k < 3; ++k) {
        if (i != k) {
          testSame(b1[k], b2[k]);
        }
      }
      for (int k = 0; k < 3; ++k) {
        testSame(b2[k], a.b(k));
      }
    }
  }
}
