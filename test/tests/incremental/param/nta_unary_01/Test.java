// Test that not all parameter caches are flushed when only
// some are affected by a dependency change.
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    A a = new A(new Named("Bo"), new Named("rt"));

    Named left = a.lr(true),
          right = a.lr(false);
    testEquals(left.getName(), "Bo");
    testEquals(right.getName(), "rt");

    // Check that A.lr(false) is not recomputed after changing A.Left.
    a.setLeft(new Named("Al"));
    testNotSame(left, a.lr(true));
    testSame(right, a.lr(false));
    testEquals("Al", a.lr(true).getName());

    left = a.lr(true);

    // Check that A.lr(true) is not recomputed after changing A.Right.
    a.setRight(new Named("ice"));
    testNotSame(right, a.lr(false));
    testSame(left, a.lr(true));
    testEquals("ice", a.lr(false).getName());
  }
}
