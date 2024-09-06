// Test debug error message for illegal collection contribution.
// .options=debug
// .result=OUTPUT_PASS
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    B b3 = new B();
    A a1 = new A(new List<B>().add(b1));
    A a2 = new A(new List<B>().add(b2).add(b3));
    A a3 = new A(new List<B>());
    Root root = new Root(new List<A>().add(a1).add(a2).add(a3));

    testThat(a3.set()).containsExactly(b1, b2, b3);

    try {
      a1.set2();
    } catch (RuntimeException e) {
      testEqual(
          "Contribution source and target do not share a common collection root node for "
          + "collection attribute A.set2().",
          e.getMessage());
      return;
    }
    fail("expected runtime exception");
  }
}
