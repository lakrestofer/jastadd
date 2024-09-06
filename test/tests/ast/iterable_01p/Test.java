// The ASTNode.astChildren() method returns an Iterable<ASTNode>.
// .grammar: {A ::= B1:B B2:B B3:B; B ::= <ID>;}
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new B("x1"), new B("y"), new B("x2"));
    testThat(a.childList()).containsExactly("x1", "x2", "y");
  }
}
