// Test using getXNoTransform(int) to access children in an NTA list child.
// This test checks that a NullPointerException is not thrown when using
// getXNoTransform(int) to access a specific child of an NTA list.
// .result=EXEC_PASS
// .options: rewrite=cnta | rewrite=regular
// .grammar: { Node ::= A* /NtaA:A*/; A ::= <Transformed:Boolean>; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Node root = new Node(new List<A>(new A(false), new A(false), new A(false)));

    root.getNtaAList(); // Must access NTA first so it is computed.

    testFalse(root.getANoTransform(0).getTransformed());
    testFalse(root.getANoTransform(1).getTransformed());
    testFalse(root.getANoTransform(2).getTransformed());

    testFalse(root.getNtaANoTransform(0).getTransformed()); // Should not cause NullPointerException.
    testFalse(root.getNtaANoTransform(1).getTransformed());
    testFalse(root.getNtaANoTransform(2).getTransformed());

    testTrue(root.getNtaA(0).getTransformed());
    testTrue(root.getNtaA(1).getTransformed());
    testTrue(root.getNtaA(2).getTransformed());
  }
}
