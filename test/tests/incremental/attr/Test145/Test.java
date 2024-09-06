// .result=OUTPUT_PASS
// .options: rewrite incremental=attr,flush,full,debug

import java.util.*;

public class Test {

  public static void main(String[] args) {

    A a = new A("a");

    // Activate rewrite
    a.a();
    
    System.out.println("-- Dependencies/Cache after a.a:");
    a.dumpDependencies();
    a.dumpCachedValues();

    // Change
    a.setName("b");
  
    System.out.println("-- Dependencies/Cache after a.setName:");
    a.dumpDependencies();
    a.dumpCachedValues();
  
  }
}
