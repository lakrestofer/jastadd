import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Object o = new ASTNode();
    Object p = new C();
    Object q = new A();
    
    test(o instanceof X);
    test(p instanceof X);
    test(q instanceof X);
  }
}
