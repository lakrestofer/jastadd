// Test that NTA proxy objects are not aliased between fresh nodes.
// See bitbucket issue 290:
// https://bitbucket.org/jastadd/jastadd2/issues/290/nta-proxy-object-aliasing-in-concurrent
// .grammar: { A ::= <ID>; B ::= <ID>; }
// .options: concurrent=true
import static runtime.Test.*;
import java.util.ArrayList;

public class Test {
  public static void main(String args[]) {
    A a = new A("oh hi");

    // Proxy object is not aliased after copying (1).
    A a2 = a.treeCopyNoTransform();
    a2.setID("bye");

    // Proxy object is not aliased after copying (2).
    A a3 = a.treeCopyNoTransform();
    a3.setID("x");

    testEqual("oh hi", a.b("one").outer());
    testEqual("bye", a2.b("one").outer());
    testEqual("x", a3.b("one").outer());

    testEqual("x", a3.b("two").outer());
    testEqual("bye", a2.b("two").outer());
    testEqual("oh hi", a.b("two").outer());

    // Proxy object is reset after makeFreshNode.
    a.makeFreshNode();
    a.setID("nope");
    testEqual("nope", a.b("one").outer());
    testEqual("nope", a.b("three").outer());
  }
}

