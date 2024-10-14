import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testFalse(new X().hasA());

		testTrue(new X(new Opt(new A())).hasA());

		A a = new A();
		X x = new X();
		x.setA(a);

		testSame(a, x.getA());
	}
}
