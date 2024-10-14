// Tests that an NTA declared in both the grammar and aspect is cached properly.
// https://bitbucket.org/jastadd/jastadd2/issue/198/component-declared-nta-in-both-ast-and
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();

		// Test caching.
		testSame(a.getB(), a.getB());

		// Test that the NTA is really the one we created in the equation.
		testTrue("NTA equation value discarded!", a.getB() != null);
	}
}
