// This test is identical to coll/multi-value_01p except that the type of the
// value expression is Iterable<String> rather than Collection<String>.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();
    a.addB(new B("X", 2));
    a.addB(new B("Y", 3));

    testThat(a.b()).containsExactly("X", "X", "Y", "Y", "Y");
  }
}
