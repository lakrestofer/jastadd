// Tests case (II) in Figure 2 in CRAG paper.
//
// .options=componentCheck |
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Root r = new Root();
    testTrue(r.a1());
    testEqual(5, r.b());
    testEqual(5, r.c1());
    testEqual(5, r.c2());
    testEqual(5, r.c3());
    testTrue(r.a2());
    testTrue(r.a3());

    Root r2 = new Root();
    testEqual(5, r2.b());  // Call b() before a1().
    testEqual(5, r2.c1());
    testEqual(5, r2.c2());
    testEqual(5, r2.c3());
    testTrue(r2.a1());
    testTrue(r2.a2());
    testTrue(r2.a3());
    testEqual(5, r2.b());
  }
}

