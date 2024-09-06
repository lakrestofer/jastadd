// Tests that an NTA declared in the grammar is cached properly.
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();

		// Declared NTA is cached.
		testSame(a.getB(), a.getB());

		// Test that the NTA is really the one we created in the equation.
		testTrue("NTA equation value discarded!", a.getB() != null);
	}
}
