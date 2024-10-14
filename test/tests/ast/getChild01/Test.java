import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		SubNode subNode = new SubNode();
		Node node = new Node(subNode);

		testSame(subNode, node.getChild(0));
	}
}
