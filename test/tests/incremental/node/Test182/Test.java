// .result=OUTPUT_PASS
// .options: rewrite incremental=node,flush,full,debug

import java.util.*;

public class Test {

  public static void main(String[] args) {
    
    C c = new C();
    A a = new A(c);
    c = a.getC();

    // Compute NTAs
    B b = c.c();
    
    System.out.println("-- Dependencies/Cache c.c():");
    a.dumpDependencies();
    a.dumpCachedValues();
    b.dumpDependencies();
    b.dumpCachedValues();
    c.dumpDependencies();
    c.dumpCachedValues();


    // Change
    b.setName("b");
  
    System.out.println("-- Dependencies/Cache after b.setName:");
    a.dumpDependencies();
    a.dumpCachedValues();
    b.dumpDependencies();
    b.dumpCachedValues();
    c.dumpDependencies();
    c.dumpCachedValues();


  }
}
