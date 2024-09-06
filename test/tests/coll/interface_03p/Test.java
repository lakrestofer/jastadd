// Tests collection attribute on interface.
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		B b = new B("0");
		A a = new A(b);

		testFalse(a.set().contains(a));
	}
}
