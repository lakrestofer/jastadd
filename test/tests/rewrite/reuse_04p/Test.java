// The correct way of reusing a child node in a rewrite result is to copy the
// child node with treeCopy().
// .options=rewrite=cnta
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Root root = new Root(new A(new B()));
    A a = root.getA();
    A aRef = a.getB().a();
    testTrue(a.isRewritten());
    testSame(a, aRef);
  }
}
