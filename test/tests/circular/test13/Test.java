// Like Test59, but for inherited attributes. (Topological evaluation of strongly
// connected components).
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {

    // Evaluate c1() before c2().
    A a = new A();
    B root = new B(a);
    testEqual(5, a.c1_a());
    testEqual(2, a.c2_a());

    // Evaluate c2() before c1().
    a = new A();
    root = new B(a);
    testEqual(2, a.c2_a());
    testEqual(5, a.c1_a());
  }
}
