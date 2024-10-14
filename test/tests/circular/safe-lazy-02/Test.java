// Using --safeLazy can degrade performance by causing attributes to be
// re-computed more than they need to be. This test demonstrates a situation
// where --safeLazy causes redundant attribute computations.
// .options: safeLazy cacheCycle=false
// .grammar: { A; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    A a = new A();

    testEqual(5, a.c());
    testEqual(6, a.bComputeCount); // A.b() would have been computed once without --safeLazy.
  }
}
