// Test that contributions from supertypes are also added to the collection.
// This is a variant of sublcass_01p where the subclass has a conditional contribution.
// If the contributor sets are not built correctly then we could miss the A contribution.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testThat(new Root(new A()).names()).containsExactly("A");
    testThat(new Root(new B()).names()).containsExactly("A");
    testThat(new Root(new B(new Opt<A>(new A()))).names()).containsExactly("A", "B", "A");
  }
}
