// Test that componentCheck does not throw an exception when using a
// non-circular parameterized attribute connecting two separate strongly
// connected circular components.
//
// .options=componentCheck
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testEqual(2, new Node().min(2, 3));
    testEqual(2, new Node().min(3, 2));
    testEqual(12, new Node().limit());
    testEqual(new Node().limit(), new Node().circ());
  }
}
