// Test that parameterized NTA on ASTNode work with 
// concurrent-evaluation. 
// See https://bitbucket.org/jastadd/jastadd2/issues/287/parameterized-nta-on-astnode-do-not-work
// .grammar: { A; B:A; }
// .options: concurrent=true | safeLazy=true
import static runtime.Test.*;

public class Test {
  public static void main(String args[]) {
    ASTNode node = new ASTNode();
    testEqual(node.b(2),node.b(2)); 
  }
}
