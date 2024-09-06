import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		node.getXList().add(new X());
		node.getXList().add(new X());
		node.getXList().add(new X());
		testEquals(3, node.getChildNoTransform(0).getNumChildNoTransform());

		Node copy = (Node) node.treeCopyNoTransform();
		testNotSame(node.getChildNoTransform(0), copy.getChildNoTransform(0));
		testEquals(0, copy.getChildNoTransform(0).getNumChildNoTransform());

		// The XList child of the copied node should *NOT* be null
		// only the children of the NTA list should not be copied
		copy = (Node) node.treeCopyNoTransform();
		testNotNull(copy.getChildNoTransform(0));
	}
}
