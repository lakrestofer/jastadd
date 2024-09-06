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
    A a = new A("a");
    dump(a, "after construction");
    dumpNodes(a, "a");

    // compute NTA
    a.d();
    dump(a, "after a.d");
    dumpNodes(a, "a");

    // compute attribute in NTA node
    B b = a.b("a");
    b.a();
    dump(a, "after a.b(String) + b.a");
    dumpNodes(a, "a");

    // trigger change propagation
    a.setName("b");
    dump(a, "a.setName");
    dumpNodes(a, "a");
  }
}
