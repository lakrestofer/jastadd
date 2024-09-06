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

  public static void main(String[] args) {
    A a = new A(new B(), "b", new D());
    dump(a, "after construction");
    dumpNodes(a, "a");

    // activate rewrite
    a.getB();
    dump(a, "after rewrite");
    dumpNodes(a, "a");

    // create link to rewritten child from sibling
    a.getD().sibling();
    dump(a, "after a.getD.sibling");
    dumpNodes(a, "a");

    // trigger change propagation
    a.setName("b");
    dump(a, "after a.setName");
    dumpNodes(a, "a");

    // activate rewrite
    a.getB();
    dump(a, "after rewrite");
    dumpNodes(a, "a");
  }
}
