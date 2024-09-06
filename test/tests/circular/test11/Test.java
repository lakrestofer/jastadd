// By default, strongly connected components are detected at evaluation time (if
// separated by a non-circular attribute), and evaluated in topological order.
// This improves performance (over evaluating as a single component) if evaluation
// starts in an outer component.
//
// .options=rewrite componentCheck | componentCheck
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
