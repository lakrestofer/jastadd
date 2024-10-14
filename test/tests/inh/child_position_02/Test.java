// Inherited equations on NTA list components can use the child index.
// .tags: nta,inh
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();
		testEqual(2, a.getB(2).value());
		testEqual(0, a.getB(0).value());
		testEqual(1, a.getB(1).value());
	}
}
