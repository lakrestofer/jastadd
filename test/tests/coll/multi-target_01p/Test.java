// A contribution can contribute to multiple nodes using the `each` keyword
// before the contribution target expression.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Root root = new Root(new Multi());
    testThat(root.uses()).isEmpty();

    Decl a = new Decl("a");
    Use aUse1 = new Use("a");
    Use aUse2 = new Use("a");
    Use aUse3 = new Use("a");
    Decl b = new Decl("b");
    Use bUse1 = new Use("b");
    Use bUse2 = new Use("b");
    Use bUse3 = new Use("b");
    root = new Root(new Multi(new List<Node>()
          .add(aUse1)
          .add(a)
          .add(bUse1)
          .add(new Multi(new List<Node>()
                .add(b)
                .add(aUse2)
                .add(bUse2)
                .add(aUse3)
                .add(bUse3)))));

    testThat(root.uses()).containsExactly(aUse1, aUse2, aUse3, bUse1, bUse2, bUse3);
    testThat(a.myUses()).containsExactly(aUse1, aUse2, aUse3);
    testThat(b.myUses()).containsExactly(bUse1, bUse2, bUse3);

    testThat(aUse1.decls()).containsExactly(a);
    testThat(aUse2.decls()).containsExactly(a);
    testThat(aUse3.decls()).containsExactly(a);
    testThat(bUse1.decls()).containsExactly(b);
    testThat(bUse2.decls()).containsExactly(b);
    testThat(bUse3.decls()).containsExactly(b);
  }
}
