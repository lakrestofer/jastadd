// Test that the generated method List.addAll() works when passing an Iterable.
// .result=COMPILE_PASS
// .grammar: { A ::= B*; B ::= <NAME>; }
import java.util.LinkedList;

import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A root = new A();

    // The B list should start out empty.
    testThat(root.getBList()).isEmpty();

    // Adding an empty list results in no change.
    root.getBList().addAll(buildIterable());
    testThat(root.getBList()).isEmpty();

    // Test adding three elements.
    root.getBList().addAll(buildIterable("1", "2", "3"));
    testThat(root.getBList()).hasSize(3);

    // Adding an empty list results in no change.
    root.getBList().addAll(buildIterable());
    testThat(root.getBList()).hasSize(3);

    // Test adding three more elements.
    root.getBList().addAll(buildIterable("1", "2", "3"));
    testThat(root.getBList()).hasSize(6);
  }

  private static Iterable<B> buildIterable(String... names) {
    LinkedList<B> list = new LinkedList<>();
    for (String name : names) {
      list.add(new B(name));
    }
    return list;
  }
}
