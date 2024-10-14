// Test transitive dependency going through an NTA: an attribute that depends
// on any part of an NTA should be recomputed when the dependency changes.
// .options: rewrite=cnta incremental=param
// .grammar: { A ::= CopyMe:B; B ::= <Name>; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new B("Marge"));

    testEquals("Marge", a.bName());

    // Check that A.bName() is recomputed after change.
    a.setCopyMe(new B("Merge"));
    testEquals("Merge", a.bName());
  }
}
