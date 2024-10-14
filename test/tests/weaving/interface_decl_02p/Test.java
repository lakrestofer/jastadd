// Test code generation for weaving an abstract method declaration in an interface.
// The method declaration should be added only to the interface declaration, not
// to types implementing the interface.
// https://bitbucket.org/jastadd/jastadd2/issues/282/abstract-interface-methods-are-added-to
// .tags: interface,weaving
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testTrue(new FieldDecl().needsQualifier());
    testFalse(new VarDecl().needsQualifier());
  }
}
