// Test that a lazy attribute is only evaluated once, and that a non-lazy attribute is
// evaluated once for each access.
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		
		for (int i = 0; i < 3; ++i) {
			node.lazyAttr();
			node.nonLazyAttr();
			testEqual(1, node.lazyEvalCount);
			testEqual(1 + i, node.nonLazyEvalCount);
		}
	}
}
