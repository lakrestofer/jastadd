// Option noComponentCheck causes all circular attributes to be evaluated
// together, even if they could be split into strongly connected components.
//
// .options: componentCheck=no
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A root;

    // c1 before c2
    root = new A();
    testEqual(5, root.c1_a());
    testEqual(2, root.c2_a());
  
    // c2 before c1
    root = new A();
    testEqual(2, root.c2_a());
    testEqual(5, root.c1_a());
  }
}
