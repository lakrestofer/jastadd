import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A(new B());

		testEqual(-1, a.getB().value());
	}
}
