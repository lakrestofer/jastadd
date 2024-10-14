// A synthesized circular attribute can depend directly on itself.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testFalse(new A().a());
  }
}
