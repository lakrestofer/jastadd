// Using getXNoTransform() to access an NTA should compute the NTA, not
// return the uninitialized value of the NTA.
// .grammar: { A ::= /B/; B; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a = new A();
    B b = a.getBNoTransform();
    testNotNull(b);
    testSame(b, a.getB());
  }
}
