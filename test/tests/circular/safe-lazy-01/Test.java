// Using the --safeLazy flag enables non-circular cached attributes in a
// circular attribute cycle.
// .options: safeLazy
// .grammar: { A; }
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testEqual(5, new A().c()); // This would fail without --safeLazy.
  }
}
