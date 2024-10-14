// Test that an attribute is correctly evaluated (rather than incorrectly cached)
// in an NTA that is built by cloning another node.
// .grammar: {P ::= A:N; N;}
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    P root = new P(new N());
    testEqual(80, root.getA().attr());
    testEqual(-80, root.getA().b().attr());
  }
}
