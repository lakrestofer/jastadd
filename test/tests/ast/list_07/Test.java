// Invoking getANoTransform(int) does not trigger rewrite on the retrieved node.
// .result=EXEC_PASS
// .options=rewrite=cnta
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a0 = new B();
    A a1 = new B();
    A a2 = new B();
    Node root = new Node(new List<A>(a0, a1, a2));

    // Avoiding rewrite:
    testSame(a0, root.getANoTransform(0));
    testSame(a1, root.getANoTransform(1));
    testSame(a2, root.getANoTransform(2));

    // Triggering rewrite:
    testNotSame(a0, root.getA(0));
    testNotSame(a1, root.getA(1));
    testNotSame(a2, root.getA(2));
  }
}
