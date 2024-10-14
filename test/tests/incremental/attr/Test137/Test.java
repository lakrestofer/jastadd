// .result=OUTPUT_PASS
// .options: rewrite incremental=attr,flush,full,debug
import java.io.*;

public class Test {

  public static void main(String[] args) {

    B b1 = new B("b", "a");
    B b2 = new B("a", "b");    

    A a = new A(new List().add(b1).add(b2));

    b1 = a.getB(0);
    b2 = a.getB(1);
    

    b1.decl();    
    System.out.println("Dependencies after b1.decl:");
    a.dumpDependencies();
    a.getChild(0).dumpDependencies();
    b1.dumpDependencies();
    b2.dumpDependencies();
    System.out.println("Cached values after b1.decl:");
    a.dumpCachedValues();
    a.getChild(0).dumpCachedValues();
    b1.dumpCachedValues();
    b2.dumpCachedValues();

    b2.decl(); 
    System.out.println("Dependencies after b2.decl:");
    a.dumpDependencies();
    a.getChild(0).dumpDependencies();
    b1.dumpDependencies();
    b2.dumpDependencies();
    System.out.println("Cached values after b2.decl:");
    a.dumpCachedValues();
    a.getChild(0).dumpCachedValues();
    b1.dumpCachedValues();
    b2.dumpCachedValues();
  
  }
}
