// Cache declarations can be used on parameterized attributes.
// This tests the short form of a cache declaration skipping the parameter names.
// See issue https://bitbucket.org/jastadd/jastadd2/issues/262/make-parameter-names-optional-in-cache
// .grammar: { A; }
import static runtime.Test.*;
public class Test {
  public static void main(String[] args) {
    A a = new A();
    testSame(a.build(1, 2.f), a.build(1, 2.f));
  }
}
