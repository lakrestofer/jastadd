// Test defining a collection with a single unconditional contribution.
// .result=OUTPUT_PASS
public class Test {
  public static void main(String[] args) {
    A a = new A(new List().add(new B()).add(new B()));
    for (B b : a.set()) {
      System.out.println("Found a B");
    }
  }
}
