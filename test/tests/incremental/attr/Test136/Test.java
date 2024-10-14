// .result=OUTPUT_PASS
// .options: rewrite incremental=attr,flush,full,debug
import java.io.*;
import java.util.*;

public class Test {

  public static void main(String[] args) {
    
    A a = new A("b");
    
    a.a0();
    a.a1();

    System.out.println("Dependencies:");
    a.dumpDependencies();
    System.out.println("Cached values:");
    a.dumpCachedValues();
    
  }
}
