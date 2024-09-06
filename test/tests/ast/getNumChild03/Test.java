// Invoking getAListNoTransform().getNumChildNoTransform() or
// getNumAChildNoTransform() does not trigger a list rewrite.
// .result=EXEC_PASS
// .options=rewrite
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node(new List().add(new B()));

		A a0 = getA_NT(node, 0);

		// trigger rewrite
		node.getAListNoTransform().getNumChildNoTransform();
		testSame(a0, getA_NT(node, 0));

		node.addA(new B());
		A a1 = getA_NT(node, 1);

		// trigger rewrite
		node.getNumANoTransform();
		testSame(a1, getA_NT(node, 1));
	}
	
	/**
	 * Get A child without invoking transformations
	 * @param node
	 * @param index
	 * @return A child
	 */
	private static A getA_NT(Node node, int index) {
		return node.getAListNoTransform().getChildNoTransform(index);
	}
}
