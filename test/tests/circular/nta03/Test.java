// Test that fixed-point evaluation uses a custom equals method to compare
// the values for a circular NTA.
// This test times out if the NTA value comparison uses reference equality, because
// a fresh A node is computed each time.
// .result=EXEC_PASS
// .grammar: { A ::= <Value:Integer>; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(10);
    testEqual(10, a.getValue());
    testEqual(10, a.min(10).getValue());
    testEqual(10, a.min(20).getValue());
    testEqual(4, a.min(4).getValue());
    testEqual(-10, a.min(-10).getValue());
  }
}
