import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		C c1 = new C();
		C c2 = new C();
		B b = new B(c1, c2);
		A a = new A(b);

		testEqual(1, c1.value());

		testEqual(2, c2.value());
	}
}
