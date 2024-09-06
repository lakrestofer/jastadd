// Caches for parameterized NTA should return the same node and be unbounded.
// .grammar: { A; }
import static runtime.Test.*;
public class Test {

  public static final int ENTRIES = 10000;

  public static void main(String[] args) {
    A a = new A();
    A[] as = new A[ENTRIES];
    for (int i = 0; i < ENTRIES; i++) {
      as[i] = a.getA(i);
    }
    for (int i = 0; i < ENTRIES; i++) {
      testSame(as[i], a.getA(i));
    }
  }
}
