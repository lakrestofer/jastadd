// By default, strongly connected components are detected at evaluation time (if
// separated by a non-circular attribute), and evaluated in topological order.
// This improves performance (over evaluating as a single component) if evaluation
// starts in an outer component.
//
// .result=COMPILE_PASSED
// .options=componentCheck
public class Test {
  public static void main(String[] args) {
    A root;

    // c1 before c2
    root = new A();
    test(root.c1_a() == 5);
    test(root.c2_a() == 2);

    // c2 before c1
    root = new A();
    test(root.c2_a() == 2);
    test(root.c1_a() == 5);
  }

  private static final void test(boolean b) {
    if (b)
      System.out.println("PASS");
    else
      System.out.println("FAIL");
  }
}
