// Two mutually circular attributes can terminate immediately.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testFalse(new A().u());
    testFalse(new A().v());
  }
}
