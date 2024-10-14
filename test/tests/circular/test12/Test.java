// Like Test58, but for inherited attributes. (Option noComponentCheck, causing
// different strongly connected components to be evaluated together.)
//
// .options: componentCheck=no
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {

    // c1 before c2
    A a = new A();
    B root = new B(a);
    testEqual(5, a.c1_a());
    testEqual(2, a.c2_a());

    a = new A();
    root = new B(a);
    testEqual(2, a.c2_a());
    testEqual(5, a.c1_a());
  }
}
