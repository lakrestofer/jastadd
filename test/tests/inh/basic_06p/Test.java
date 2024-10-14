import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A left = new A();
		A right = new A();
		Node node = new Node(left, right, new B());

		testEqual(77, left.value());
		testEqual(-1, right.value());
		testEqual(0xBEEF, node.getB().value());
	}
}
