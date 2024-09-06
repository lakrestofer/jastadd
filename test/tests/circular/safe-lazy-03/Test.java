// This test checks that --safeLazy cached attribute values are reused when
// the cached attribute is evaluated multiple times on the same cycle.
// .options: safeLazy cacheCycle=false
// .grammar: { A; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();

    testEqual(15, a.c());
    testEqual(6, a.bComputeCount); // A.b() would have been computed 18 times if it was uncached.
  }
}
