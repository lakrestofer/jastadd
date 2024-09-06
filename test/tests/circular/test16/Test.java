// The initial value can be an object.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();
    testSame(a.emptyHashSet(), a.m());
    testSame(a.emptyHashSet(), a.n());
  }
}

