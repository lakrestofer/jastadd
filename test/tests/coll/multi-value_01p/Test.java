// Test that collection contributions can contribute multiple values using
// the `each` keyword.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();
    a.addB(new B("X", 2));
    a.addB(new B("Y", 3));

    testThat(a.b()).containsExactly("X", "X", "Y", "Y", "Y");
  }
}
