// Tests an inherited attribute on a parameterized NTA.
// .tags: nta,inh,parameterized
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A(new B());

		testEqual(0xBEEF, a.selectB(0).value());
		testEqual(0xBEEF, a.selectB(1).value());
		testEqual(0xBEEF, a.selectB(2).value());
		testEqual(0xBEEF, a.selectB(3).value());
		testEqual(0xBEEF, a.selectB(4).value());
		testEqual(-1, a.getB().value());
	}
}
