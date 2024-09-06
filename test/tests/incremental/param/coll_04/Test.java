// Incremental collection attribute on non-root node with siblings.
// .options: rewrite=cnta incremental=param

import java.util.*;
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    int[] sizes = { 0, 0, 1, 2, 3, 4, 5 };
    A a = new A();
    for (int i = 0; i < sizes.length; ++i) {
      B b = new B("b"+0, new List<C>());
      for (int j = 0; j < sizes[i]; ++j) {
        b.addC(new C(String.format("c%d_%d", i+1, j+1)));
      }
      a.addB(b);
    }

    for (int i = 0; i < sizes.length; ++i) {
      testEqual(sizes[i], a.getB(i).getNumC());
    }

    checkCollections(a, "before");

    // Transform:
    a.getB(6).getCList().removeChild(4);
    a.getB(2).getCList().removeChild(1);
    a.getB(4).getCList().removeChild(2);
    a.getB(0).addC(new C("c_new1"));
    a.getB(0).addC(new C("c_new2"));

    checkCollections(a, "after");
  }

  static void checkCollections(A a, String phase) {
    for (B b : a.getBList()) {
      testEqual(phase + ": " + b.getNAME(), b.getNumC(), b.cs().size());
    }
  }
}
