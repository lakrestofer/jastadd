// Test flushing of overloaded attributes equations.
// .options: rewrite=cnta incremental=param
// .grammar: { A ::= <ID:String>; B : A; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new B("Bu");
    testEquals(a.name(), "DrBuBu");
    a.setID("Ba");
    testEquals(a.name(), "DrBaBa");
  }
}
