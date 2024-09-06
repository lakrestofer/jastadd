// An attribute that is circular, but which is not defined as such, results in an
// exception at evaluation time (confer Test44). Such an exception is raised also
// when other attributes on the cycle are declared circular.
//
// .options=rewrite componentCheck
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A root  = new A();
    try{
      root.a("x");
      root.a(1);
      fail("Expected circularity exception.");
    } catch (RuntimeException e) {
      testEqual("Circular definition of attribute A.b().", e.getMessage());
    }
  }
}
