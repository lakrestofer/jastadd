import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A left = new A();
		A right = new A();
		Node node = new SubNode(left, right);

		test(64 != left.value());

		testEqual(1024, right.value());
	}
}
