// It should not be possible to make an NTA uncached.
// .grammar: { A ::= /A/; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a = new A();
    testSame(a.getA(), a.getA());
  }
}
