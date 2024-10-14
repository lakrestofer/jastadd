import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node(new Node());
		Node copy = (Node) node.getNode().treeCopyNoTransform();

		test("The parent reference should be null after full copy",
				copy.getParent() == null);
	}
}
