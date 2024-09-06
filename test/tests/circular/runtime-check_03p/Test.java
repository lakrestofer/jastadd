// Test that componentCheck does not throw an exception when using a
// non-circular attribute in a circular evaluation cycle, as long as the
// attribute does not connect to the rest of the circular component.
//
// .options=componentCheck
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testEqual(6, new Node().limit());
    testEqual(new Node().limit(), new Node().circ());
  }
}
