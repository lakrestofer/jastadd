// Test that contributions from supertypes are also added to the collection.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testThat(new Root(new A()).names()).containsExactly("A");
    testThat(new Root(new B()).names()).containsExactly("A", "B");
  }
}
