import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		test(!new X().hasMyA());

		test(new X(new Opt(new A())).hasMyA());

		A a = new A();
		X x = new X();
		x.setMyA(a);

		testSame(a, x.getMyA());
	}
}
