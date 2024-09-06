// .result=OUTPUT_PASS
// .options: rewrite incremental=node,flush,full,debug

import java.util.*;

public class Test {

  public static void main(String[] args) {

    A a = new A("a", new B());

    // Activate rewrite
    a.d();
    B b = a.makeB();
    b.a();
    
    System.out.println("-- Dependencies/Cache after a.makeB and b.a:");
    a.dumpDependencies();
    a.dumpCachedValues();
    b.dumpDependencies();
    b.dumpCachedValues();

    // Change
    a.setName("b");
  
    System.out.println("-- Dependencies/Cache after a.setName:");
    a.dumpDependencies();
    a.dumpCachedValues();
    b.dumpDependencies();
    b.dumpCachedValues();
  
  }
}
