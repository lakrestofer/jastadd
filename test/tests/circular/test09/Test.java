// Several mutually circular attributes are evaluated in a common cycle. When one
// attribute has converged, the rest have converged too.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A node = new A();

    testEqual(true, node.a());
    testEqual(true, node.b());
    testEqual(true, node.c());
  }
}
