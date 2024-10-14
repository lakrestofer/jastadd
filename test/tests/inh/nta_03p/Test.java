// .tags: nta,inh
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A(new B());

		testEqual(0xF00, a.getB().value());
		testEqual(0xBAA, a.getTheB().value());
	}
}
