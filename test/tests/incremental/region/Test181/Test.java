// .result=OUTPUT_PASS
// .options: rewrite=cnta incremental=region,flush,full,debug

import java.util.*;

public class Test {

  private static void dump(ASTNode root, String test) {
    String line = "";
    for (int i = 0; i < test.length(); i++) {
      line += "-";
    }
    System.out.println(" " + line + "\n| " + test + "\n " + line);
    root.cleanupListenersInTree();
    root.dumpDepsInTree();
  }
 
  public static void dumpNodes(ASTNode node, String prefix) {
    System.out.println("node: " + prefix + "=" + str(node));
    node.dumpCachedValues();
    System.out.println("node: " + prefix + ".parent=" + str(node.getParent()));
    for (int k = 0; k < node.getNumChildNoTransform(); k++) {
      dumpNodes(node.getChildNoTransform(k), prefix + "/child[" + k + "]");
    }
  }
 
  public static String str(ASTNode node) {
    return node != null ? node.relativeNodeID() : "null";
  }

  // TODO(emso): This test looks like it is doing everything right but the
  // dumped info does not include the intrinsic attribute. NB! The intrinsic
  // attribute should not be a fresh node but a node already in the AST.
  public static void main(String[] args) {
    A a = new A(new B(), new B());
    dump(a, "after construction");
    dumpNodes(a, "a");

    // compute NTA
    a.d();
    dump(a, "after a.d");
    dumpNodes(a, "a");

    B b = a.getNode();
    b.a();
    dump(a, "after b.a");
    dumpNodes(a, "a");

    // trigger change propagation
    a.setNode(null);
    dump(a, "after a.setNode(null)");
    dumpNodes(a, "a");
  }
}
