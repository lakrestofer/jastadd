// Test a circular attribute with an uncached attribute in the cycle.
// This test shows a case where an original aspect is extended, and the
// extension introduces a circularity.
// .options: visitCheck=false
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testEqual(10, new B().x());
  }
}
