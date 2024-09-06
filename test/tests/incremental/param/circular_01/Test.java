// Test for NullPointerException in evaluation of parameterized circular
// attribute with incremental=param.
// See https://bitbucket.org/jastadd/jastadd2/issues/296/parameterized-circular-attributes-fail-in
// .grammar: { Root; }
// .options: | incremental=param rewrite=cnta
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Root root = new Root();
    testTrue(root.circular_unparameterized());
    testTrue(root.not_circular_parameterized(true));
    testTrue(root.circular_parameterized(true));
  }
}
