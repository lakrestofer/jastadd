import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node("foo", "bar");
		testEqual("bar", node.getToken2());
		testEqual("foo", node.getToken1());
	}
}
