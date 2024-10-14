import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		List list = new List();

		testNull(list.getChild(0));

		Node node0 = new Node();
		Node node1 = new Node();
		Node node2 = new Node();

		list.addChild(node0);
		list.addChild(node1);
		list.addChild(node2);

		testSame(node0, list.getChild(0));
		testSame(node1, list.getChild(1));
		testSame(node2, list.getChild(2));
		testNull(list.getChild(3));
	}
}
