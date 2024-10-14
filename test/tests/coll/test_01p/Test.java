// Tests a collection attribute defined with a root node, and a single conditional contribution.
// .result=OUTPUT_PASS
public class Test {

  public static void main(String[] args) {
    // Test that the Bs are found in the collection.
    A a = new A(new List().add(new B()).add(new B()));
    for (B b : a.set()) {
      System.out.println("Found a B");
    }
  }
}
