// Invoking getAListNoTransform().getNumChild() or getNumAChild()
// triggers rewrites in legacy rewrite mode.
// .result=EXEC_PASS
// .options=rewrite=regular
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Node node = new Node(new List().add(new B()));

    A a0 = getA_NT(node, 0);

    // Accessing child count triggers rewrite.
    node.getAListNoTransform().getNumChild();
    testNotSame(a0, getA_NT(node, 0));

    node.addA(new B());
    A a1 = getA_NT(node, 1);

    // Accessing child count triggers rewrite.
    node.getNumA();
    testNotSame(a1, getA_NT(node, 1));
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
