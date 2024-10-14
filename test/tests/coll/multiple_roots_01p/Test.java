// Test collection attribute with implicit root when there are multiple roots in the grammar.
// In this case all grammar roots have a common supertype.
// https://bitbucket.org/jastadd/jastadd2/issues/294/remove-the-error-multiple-tree-roots-to
// .result: EXEC_PASS
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    // NB: We need to trees with multiple levels to make sure the root finding
    // does work in subtrees.
    testThat(new RootA(tree1()).literals())
        .containsExactly("x", "y", "a", "b", "sporknid");
    testThat(new RootB(tree1()).literals())
        .containsExactly("x", "y", "a", "b", "sporknid");
  }

  static Node tree1() {
    return
      branch(
        branch(
          leaf("x"),
          leaf("y")
        ),
        branch(
          leaf("a"),
          branch(
            leaf("b"),
            leaf("sporknid")
          )
        )
      );
  }

  static Node branch(Node left, Node right) {
    return new Branch(left, right);
  }

  static Node leaf(String value) {
    return new Leaf(value);
  }
}
