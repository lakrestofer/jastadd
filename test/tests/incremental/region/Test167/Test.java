// .result=OUTPUT_PASS
// .options: rewrite=cnta incremental=region,flush,full,debug
import java.io.*;
import java.util.*;

public class Test {

  private static void dump(A a, String test) {
    String line = "";
    for (int i = 0; i < test.length(); i++) {
      line += "-";
    }
    System.out.println(" " + line + "\n| " + test + "\n " + line);
    a.cleanupListeners();
    a.dumpDependencies();
  }

  public static void main(String[] args) {
    
    A a = new A("b");
    
    a.a0();
    dump(a, "a.a0");

    a.a1();
    dump(a, "a.a1");
  }
}
