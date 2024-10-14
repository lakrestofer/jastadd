// Tests that an optional NTA declared in both the grammar and aspect is cached properly.
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();

		// Declared optional grammar NTA is cached.
		testSame(a.getBOpt(), a.getBOpt());

		// Test that the NTA is really the one we created in the equation.
		testEqual("NTA equation value discarded!", 1, a.getBOpt().getNumChild());
	}
}
