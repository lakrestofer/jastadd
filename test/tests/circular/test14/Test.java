// Two parameterized circular attributes can be mutually dependent.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A root  = new A();
    testTrue(root.a("x"));
    testEqual(1, root.a(1));
  }
}
