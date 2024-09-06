// Cache declarations can be used on parameterized attributes.
// .grammar: { A; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a = new A();
    testSame(a.build(1), a.build(1));
  }
}
