// .result=OUTPUT_PASS
// .options: rewrite=cnta incremental=region,flush,full,debug
import java.io.*;

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
    A a = new A(new List(new B("b", "a"), new B("a", "b")));
    B b1 = a.getB(0);
    B b2 = a.getB(1);
    dump(a, "after construction");    

    b1.decl();    
    dump(a, "b1.decl");    

    b2.decl(); 
    dump(a, "b2.decl");
  }
}
