import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		X x = node.getX();
		testSame(x, node.getChildNoTransform(0));

		// The X NTA of the copy should not be same as the original
		Node copy = (Node) node.treeCopyNoTransform();
		testNotSame(x, copy.getChildNoTransform(0));

		// The X NTA of the copy should be null if it is
		// accessed using NoTransform
		copy = (Node) node.treeCopyNoTransform();
		testNull(copy.getChildNoTransform(0));
	}
}
