// .result=EXEC_PASSED
// .
// .# rewrite option needed for is$Final
// .options=rewrite
import static runtime.Test.*;
public class Test {
	public static void main(String[] args) {
		Node n = new Node();

		test("Node should be final before fullCopy", n.is$Final);

		n = (Node) n.treeCopyNoTransform();

		test("Nodes should not be final after fullCopy", !n.is$Final);
	}
}
