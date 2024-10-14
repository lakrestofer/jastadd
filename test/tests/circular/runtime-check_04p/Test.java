// Test that componentCheck does not throw an exception when using a
// non-circular parameterized attribute in a circular evaluation cycle, as long
// as the attribute does not connect to the rest of the circular component.
//
// .options=componentCheck
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testEqual(2, new Node().min(2, 3));
    testEqual(2, new Node().min(3, 2));
    testEqual(6, new Node().circ());
  }
}
