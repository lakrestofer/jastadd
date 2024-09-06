// Test defining a collection without a root clause and a single unconditional contribution.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new List()
        .add(new B("B1"))
        .add(new B("B2")));
    testEqual(2, a.set().size());
    testEqual(true, a.set().contains("B1"));
    testEqual(true, a.set().contains("B2"));
  }
}
