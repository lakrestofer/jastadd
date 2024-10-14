// An interface can have a synthesized attribute.  AST classes implementing an
// interface with attributes can supply equations.
// .grammar: { Node; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Node node = new Node();
    testFalse(node.attr());
    testFalse(node.attr2());
  }
}
