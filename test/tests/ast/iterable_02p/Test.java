// List<T> implements Iterable<T>.
// .grammar: {A ::= B*; B ::= <ID>;}
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A(new List<B>().add(new B("8")).add(new B("...")).add(new B("foobar")));
    testThat(a.childList()).containsExactly("8", "foobar", "...");
  }
}
