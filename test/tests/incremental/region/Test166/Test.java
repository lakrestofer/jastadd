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

    a.a2();
    dump(a, "a.a2");

    a.a3();
    dump(a, "a.a3");

    a.a4();
    dump(a, "a.a4");

    a.a5();
    dump(a, "a.a5");

    a.a6();
    dump(a, "a.a6");

    a.a7();
    dump(a, "a.a7");

    a.a8();
    dump(a, "a.a8");
  }
}
