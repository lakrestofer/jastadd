import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		testEqual(13, node.value());

		node = new SubNode();
		testEqual(-1, node.value());
	}
}
