// ASTNode instances can contribute to a collection.
// See https://bitbucket.org/jastadd/jastadd2/issues/245/collection-attribute-contributions-on
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    B b3 = new B();
    C c1 = new C(b2);
    C c3 = new C(b3);
    C c2 = new C(c3);
    testThat(new A(b1).bNodes()).containsExactly(new B[] { b1 });
    testThat(new A(c1).bNodes()).containsExactly(c1, b2);
    testThat(new A(c2).bNodes()).containsExactly(c2, c3, b3);
  }
}
