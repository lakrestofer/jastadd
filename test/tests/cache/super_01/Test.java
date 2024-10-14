// Tests that an attribute is
// cached both in its superclass and in the subclass and that these caches are different.
// .grammar: { A; B:A;}
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    B b = new B();
    testTrue(b.different());
  }
}
