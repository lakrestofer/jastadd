// AST NTAs are always memoized, even if not declared lazy.
// This behaviour was changed to fix this issue:
// https://bitbucket.org/jastadd/jastadd2/issues/72/old-nta-syntax-requires-lazy
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();

    // Non-lazy AST NTA is memoized.
    testSame(a.getB(), a.getB());

    // Lazy AST NTA is memoized.
    testSame(a.getLazyB(), a.getLazyB());
  }
}
