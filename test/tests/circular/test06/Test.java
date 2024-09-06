// An inherited circular attribute can depend directly on itself.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b = new B();
    A a = new A(b);
    testFalse(b.t());
  }
}
