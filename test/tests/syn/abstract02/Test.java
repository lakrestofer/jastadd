import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new SubNode();
		testEqual(3212988, node.value());
		testFalse(node.attr(true));
	}
}
