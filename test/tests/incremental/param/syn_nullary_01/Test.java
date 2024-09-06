// Simple synthesized attribute correctness test.
// Check correct incremental computation of a chain of syn attributes
// within one node.
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A("a0");

    testFalse(a.a0());
    a.setName("a");
    testTrue(a.a0());

    a.setName("a1");
    testFalse(a.a1());
    a.setName("a");
    testTrue(a.a1());

    a.setName("a2");
    testFalse(a.a2());
    a.setName("a");
    testTrue(a.a2());

    a.setName("a3");
    testFalse(a.a3());
    a.setName("a");
    testTrue(a.a3());

    a.setName("a4");
    testFalse(a.a4());
    a.setName("a");
    testTrue(a.a4());

    a.setName("a5");
    testFalse(a.a5());
    a.setName("a");
    testTrue(a.a5());

    a.setName("a6");
    testFalse(a.a6());
    a.setName("a");
    testTrue(a.a6());
  }
}
