import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		SubNode subNode = new SubNode();
		Node node = new Node();

		testNull(node.getSubNode());

		node.setSubNode(subNode);

		testSame(subNode, node.getSubNode());
	}
}
