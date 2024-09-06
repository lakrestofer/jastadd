// A parameterized NTA can have a `null` value and that value is not added to
// the proxy object when rewrites are enabled.
// This is an implementation detail, but it is redundant to store the null values
// in the proxy object so an efficient implementation should pass this test.
// When using circular NTA rewrites, the proxy object does not store any
// references to the nodes using it as proxy parent.
// .options=rewrite=true
import static runtime.Test.*;

public class Test {
  public static void main(String args[]) {
    A a = new A();

    a.b(2);
    a.b(0);
    a.b(-1);
    a.b(1);

    testEqual(3, a.numProxyRefs());
    test("The NTA value list should not contain a null value", !a.hasNullBValue());
  }
}
