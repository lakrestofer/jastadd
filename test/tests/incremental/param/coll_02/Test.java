// Incremental collection attribute on non-root node.
// .options: rewrite=cnta incremental=param
import java.util.*;
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A(new B(new List(new C("0"), new C("1"))));

    Collection<C> cs = a.getB().b();

    // Check that B.b() is not recomputed without change:
    testSame(cs, a.getB().b());
    testThat(names(cs)).containsExactly("0", "1");

    // Transformation.
    a.getB().addC(new C("x"));

    // Check.
    testThat(names(a.getB().b())).containsExactly("0", "1", "x");
  }

  static Collection<String> names(Iterable<C> cs) {
    ArrayList<String> nm = new ArrayList<String>();
    for (C c : cs) {
      nm.add(c.getName());
    }
    return nm;
  }
}
