// Test incremental recompute granularity for inherited attribute (preorder traversal).
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    Node l1 = new Leaf("1"),
         l2 = new Leaf("2"),
         l3 = new Leaf("3"),
         l4 = new Leaf("4");
    Branch b1 = new Branch(l1, l2);
    Branch root = new Branch(b1, l3);

    Value<ASTNode>
      pb1 = b1.pred(),
      p1 = l1.pred(),
      p2 = l2.pred(),
      p3 = l3.pred();

    // Check that pred() is memoized.
    testSame(pb1, b1.pred());
    testSame(p1, l1.pred());
    testSame(p2, l2.pred());
    testSame(p3, l3.pred());

    // Check correct pred() values.
    testSame(root, pb1.x);
    testSame(b1, p1.x);
    testSame(l1, p2.x);
    testSame(l2, p3.x);

    // Transform.
    root.setRight(new Leaf("x")); // Remove l3.
    root.setRight(new Branch(l4, l3));

    // Check that not only affected pred() instances are recomputed.
    testSame(pb1, b1.pred());
    testSame(p1, l1.pred());
    testSame(p2, l2.pred());
    testNotSame(p3, l3.pred());
    testSame(l2, l4.pred().x.pred().x);
    testSame(l4, l3.pred().x);
  }
}
