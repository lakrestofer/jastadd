// Tests for nonterminal attributes; both basic and parameterized.
// Note: rewrite flag required in order to test the final flag.
// .options=rewrite
// .tags: nta,inh
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
	  // NTA: Caching.
    A a = new A();
    a.is$Final(false);

    // Non final grammar nta is not cached.
    testNotSame(a.getB(), a.getB());

    // Final grammar nta is cached.
    testSame(a.getBFinal(), a.getBFinal());

    // Declared nta is cached.
    testSame(a.b(), a.b());

    // Declared indexed nta is cached.
    testSame(a.selectB(0), a.selectB(0));
    testSame(a.selectB(1), a.selectB(1));
    testNotSame(a.selectB(0), a.selectB(1));

    // Non final attribute is not cached.
    testNotSame(a.object(), a.object());

    // Final attribute is cached.
    testSame(a.objectFinal(), a.objectFinal());

    testEqual(-1, a.getBFinal().value());
    testEqual(-1, a.b().value());
    testEqual(-2, a.selectB(0).value());
    testEqual(-2, a.selectB(1).value());
  }
}
