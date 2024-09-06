// Tests that a synthesized NTA can not be changed to uncached.
// .grammar: { A; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a = new A();
    testSame(a.ntaA(), a.ntaA());
  }
}
