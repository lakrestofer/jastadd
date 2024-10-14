// Test that the NTA cache field is used to access an NTA children
// rather than the child vector in concurrent evaluation.
// See: https://bitbucket.org/jastadd/jastadd2/issues/299/faulty-code-generation-with-concurrent
// .options: concurrent
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new B();
    testEqual("D1", a.getD1().getID());
    testEqual(2, a.getD2List().getNumChild());
    testEqual("D3", a.getD3Opt().getChild(0).getID());
  }
}
