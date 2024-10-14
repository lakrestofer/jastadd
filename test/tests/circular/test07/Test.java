// A synthesized and an inherited attribute may be mutually circular.
//
// .result=OUTPUT_PASS
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b = new B();
    A a = new A(b);

    testTrue(a.x());
    testTrue(b.y());
  }
}
