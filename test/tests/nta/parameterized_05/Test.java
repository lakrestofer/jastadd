// Parameterized NTAs do not store references from the proxy object to NTA
// values in non-rewrite and circular NTA rewrite mode.  This is an
// implementation detail, but it is redundant to store the child references in
// circular NTA rewrite mode, so an efficient implementation should pass this
// test.
// .options=rewrite=cnta
import static runtime.Test.*;

public class Test {
  public static void main(String args[]) {
    A a = new A();

    a.b(2);
    a.b(0);
    a.b(-1);
    a.b(1);

    testEqual(0, a.numProxyRefs());
  }
}
