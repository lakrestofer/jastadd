// A contributor can contribute to different target with different values.
// See issue https://bitbucket.org/jastadd/jastadd2/issues/326/multiple-contribution-to-different-targets
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a1 = new A();
    A a2 = new A();
    B b = new B();
    Root r = new Root(a1, a2, b);

    testThat(a1.nbrs()).containsExactly(1);
    testThat(a2.nbrs()).containsExactly(2);
  }
}
