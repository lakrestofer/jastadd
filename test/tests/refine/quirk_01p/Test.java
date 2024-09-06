// Tests the pattern matching of the "refined" keyword in JastAdd2.  JastAdd2 uses
// pattern matching to replace the "refined" keyword with the method name of the
// refined method. This pattern also works inside string literals and comments.
// The pattern matching should be sensitive to word boundaries so that for example
// the substring "refined" in the string "unrefined" does not match this pattern.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Node node = new Node();
    node.toBeRefined();
    testEqual("refined_A1_Node_toBeRefined", node.str[0]);
    testEqual("aTest.Node.toBeRefined", node.str[1]);
    testEqual("Test.Node.toBeRefinedd", node.str[2]);
    testEqual("refined_A1_Node_toBeRefined", node.str[3]);
    testEqual("unrefined", node.str[4]);
    testEqual("rrrefineddd", node.str[5]);
    testEqual("refinedrefined", node.str[6]);
  }
}
