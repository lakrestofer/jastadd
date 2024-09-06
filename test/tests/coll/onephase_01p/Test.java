// This test checks that the @OnePhase annotation for collection attributes works
// as intended.  The @OnePhase annotation should make the collection attribute
// find contributors and compute contributions in a single traversal of the AST.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Program program = new Program();
    program.addCompilationUnit(new CompilationUnit("Tomato"));
    program.addCompilationUnit(new CompilationUnit("Potato"));

    // Test that the condition eval counter is at 0 before evaluation.
    testEqual(0, program.conditionEvals);

    // Evaluate the collection attribute and check the value.
    testEqual(1, program.problems().size());
    testEqual("Problem: Tomato", program.problems().iterator().next().message);

    // Check that only two condition evaluations were made (the minimum for this example).
    testEqual(2, program.conditionEvals);
  }
}
