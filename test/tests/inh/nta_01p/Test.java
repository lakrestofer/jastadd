// This test checked using child index in a parameterized NTA, but this feature has
// been disabled.
// See issue https://bitbucket.org/jastadd/jastadd2/issues/205/remove-child-index-for-inherited-equation
//
// Old test description:
// The parameter to a parameterized NTA inh equation is the child position of the
// NTA value in the NTA value list. It is not the parameter corresponding to that
// particular NTA value.
// .tags: nta,inh
// .result=JASTADD_FAIL
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A(new B());

		testEqual(0, a.selectB(2).value());
		testEqual(1, a.selectB(1).value());
		testEqual(2, a.selectB(0).value());
		testEqual(3, a.selectB(1000).value());
		testEqual(4, a.selectB(-711).value());
		testEqual(5, a.selectB(0xBEEF).value());
		testEqual(-1, a.getB().value());
	}
}
