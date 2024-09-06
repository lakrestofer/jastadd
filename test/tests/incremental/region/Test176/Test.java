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
 
  public static void main(String[] args) {
    A a = new A("a");
    dump(a, "after construction");

    // Activate rewrite
    a.a();
    dump(a, "after a.a");
    a.dumpCachedValues();

    // Trigger change propagation
    a.setName("b");
    dump(a, "after a.setName");
    a.dumpCachedValues();
  }
}
