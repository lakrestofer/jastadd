// Reusing a child node in the result of a rewrite is bad, since incorrect attribute values
// can then be cached in the reused node.
// .options=rewrite=cnta
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Root root = new Root(new A(new B()));
    A a = root.getA();
    A aRef = a.getB().a();
    testTrue(a.isRewritten());

    // The rewrite condition evalutes B.a() in the bottom value of the rewrite attirubte, then
    // the result is cached. So the B.a() attribute is now incorrect. This is expected. To
    // fix this the child must be copied with treeCopy().
    testNotSame(a, aRef);
  }
}
