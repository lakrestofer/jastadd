// Test two collection attributes on the same node.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new List()
        .add(new B("B1"))
        .add(new B("B2")));
    testEqual(2, a.set().size());
    testEqual(true, a.set().contains("B1"));
    testEqual(true, a.set().contains("B2"));
    testEqual(2, a.set2().size());
    testEqual(true, a.set2().contains("B1_2"));
    testEqual(true, a.set2().contains("B2_2"));
  }
}
