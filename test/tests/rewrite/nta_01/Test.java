// Test that rewrites trigger when accessing an NTA child.
// .options: rewrite=regular | rewrite=cnta
// .grammar: { X ::= /A/; A ::= <Transformed:Boolean>; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    testTrue(new X().getA().getTransformed());
  }
}
