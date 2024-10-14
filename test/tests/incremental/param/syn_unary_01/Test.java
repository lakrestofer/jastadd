// Test dependency tracking for unary parameterized synthesized attribute.
// .options: rewrite=cnta incremental=param
// .grammar: { A ::= <Name>; }
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A("Bart");
    testEquals(a.c("Ms"), "MsBart");
    testEquals(a.c("Dr"), "DrBart");

    // Check that the attributes are recomputed after change.
    a.setName("Bort");
    testEquals(a.c("Ms"), "MsBort");
    testEquals(a.c("Dr"), "DrBort");
  }
}
