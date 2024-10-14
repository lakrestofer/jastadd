// Test incremental computation of collection attribute when adding children.
// https://bitbucket.org/jastadd/jastadd2/issues/302/missed-incremental-update-of-collections
// .options: rewrite=cnta incremental=param

import java.util.*;
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    int N = 10;
    A a = new A();
    for (int i = 0; i < N; ++i) {
      a.addB(new B("b"+i, new List<C>()));
    }

    // Start with list of empty Bs (no Cs).
    checkCollections(a, "before");

    // Transform:
    a.getB(0).addC(new C("c1"));
    a.getB(3).addC(new C("c2"));
    a.getB(3).addC(new C("c3"));
    a.getB(5).addC(new C("c4"));

    checkCollections(a, "after");
  }

  static void checkCollections(A a, String phase) {
    for (B b : a.getBList()) {
      testEqual(phase + "@" + b.getNAME(), b.getNumC(), b.cs().size());
    }
  }
}
