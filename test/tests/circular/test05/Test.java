// Two mutually circular attribute may require an iteration before terminating.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testTrue(new A().x());
    testTrue(new A().y());
  }
}
