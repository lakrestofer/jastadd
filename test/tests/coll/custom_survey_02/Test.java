// Omitting super.collectContributors() in all custom survey blocks means that
// the default collection survey code won't be called to search all
// children for contributions.
// .grammar: { A ::= B*; B; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    B b1 = new B();
    B b2 = new B();
    A a = new A(new List<B>(b1, b2));

    testThat(a.bList()).containsExactly(a.implicitB());

    testThat(a.bList2()).containsExactly(b1, b2, a.implicitB());
  }
}
