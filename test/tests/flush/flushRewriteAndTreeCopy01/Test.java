// A complicated test case combining flush, rewrite and treeCopy. 
// treeCopy invokes clone that in turn flushes caches. Clone should not 
// flush rewrite cache since this may change the original node.
// .options: rewrite flush=full 
import static runtime.Test.*;

public class Test {
  public static void main(String args[]) {
    Root r = new Root(new A(new B()));

    r.ntaA();

    testNotSame(r.ntaA(), r.getA());
    testSame(r.getA(), r.getA().getB().getParent());
    testSame(r.ntaA(), r.ntaA().getB().getParent());
  }
}
