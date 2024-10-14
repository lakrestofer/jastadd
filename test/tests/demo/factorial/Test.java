// Factorial function as a circular attribute.
// .grammar: { A; }

import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testEqual(5040, new A().factorial(7));
  }
}
