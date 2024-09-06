import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		X x = new X();
		node.setX(x);
		testSame(x, node.getChildNoTransform(0).getChildNoTransform(0));

		Node copy = (Node) node.treeCopyNoTransform();
		testNotSame(x, copy.getChildNoTransform(0).getChildNoTransform(0));

		// The Opt child of the copied node should *NOT* be null!
		copy = (Node) node.treeCopyNoTransform();
		testNotNull(copy.getChildNoTransform(0));
	}
}
