// Test component check exception caused by circularity through rewrite.
// .options=rewrite=cnta, componentCheck
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Root root = new Root(new A(new B()));
    try {
		  root.getA();
      fail("Expected component check exception.");
    } catch (RuntimeException e) {
      testEqual("Circular definition of attribute B.a().", e.getMessage());
    }
	}
}
