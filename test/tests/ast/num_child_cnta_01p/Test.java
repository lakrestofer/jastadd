// Invoking getAListNoTransform().getNumChild() or getNumAChild() does not
// trigger rewrites in cnta rewrite mode.
// .result=EXEC_PASS
// .options=rewrite=cnta
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Node node = new Node(new List().add(new B()));

    A a0 = getA_NT(node, 0);

    // Accessing child count does not trigger rewrite.
    node.getAListNoTransform().getNumChild();
    testSame(a0, getA_NT(node, 0));

    node.addA(new B());
    A a1 = getA_NT(node, 1);

    // Accessing child count does not trigger rewrite.
    node.getNumA();
    testSame(a1, getA_NT(node, 1));

    // Accessing the child triggers rewrite.
    testNotSame(a0, node.getA(0));
    testNotSame(a1, node.getA(1));
  }
  
  /**
   * Get an A child without invoking rewrite.
   * @param node
   * @param index
   * @return A child
   */
  private static A getA_NT(Node node, int index) {
    return node.getAListNoTransform().getChildNoTransform(index);
  }
}
