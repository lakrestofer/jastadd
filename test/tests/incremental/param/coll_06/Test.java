// Regression test for error in incremental update.
// The issue was in part caused by missing dependencies for empty collection instances.
// https://bitbucket.org/jastadd/jastadd2/issues/303/nullpointerexception-caused-by-empty
// .options: rewrite=cnta incremental=param

import java.util.*;
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    int N = 6;
    A a = new A();
    for (int i = 0; i < N; ++i) {
      a.addB(new B("b"+i));
    }

    a.addC(new C("c1", 1));
    a.addC(new C("c2", 2));
    a.addC(new C("c3", 3));
    a.addC(new C("c4", 5));

    checkCollections(a, "before", 0, 1, 1, 1, 0, 1);

    // Transform:
    a.getBList().removeChild(2);

    checkCollections(a, "after", 0, 1, 1, 1, 0);
  }

  static void checkCollections(A a, String phase, int... sizes) {
    for (int i = 0; i < sizes.length; ++i) {
      testEqual(phase + "@" + a.getB(i).getNAME(), sizes[i], a.getB(i).cs().size());
    }
  }
}
