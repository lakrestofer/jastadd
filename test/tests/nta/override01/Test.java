import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new SubA(new B());
		testFalse(a.getRight().value());
		testTrue(a.getLeft().value());
		testEqual(1, a.getNumChild());
	}
}
