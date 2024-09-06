// It is possible to refine a method and use other modifiers (e.g. final) on the
// parameters of the new method.
// .grammar: { X; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testEqual(16, new X().addSomething(3));
		testNull(new X().returnSomething(new ASTNode()));
	}
}
