//  Attributes that are circular, without being defined as such, result in an
//  exception at evaluation time.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b = new B();
    A a = new A(b);
    try {
      a.a();
      fail("Expected circularity exception.");
    } catch (Exception e) {
      testEqual("Circular definition of attribute A.a().", e.getMessage());
    }
    try {
      b.b();
      fail("Expected circularity exception.");
    } catch (Exception e) {
      testEqual("Circular definition of attribute B.b().", e.getMessage());
    }
  }
}
