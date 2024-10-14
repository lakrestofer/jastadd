// Test that a lazy attribute is only evaluated once, and that a non-lazy attribute is
// evaluated once for each access.
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();

    testEqual(0, node.lazyAttr());
    testEqual(0, node.lazyAttr());
    testEqual(0, node.lazyAttr());
		
    testEqual(0, node.nonLazyAttr());
    testEqual(1, node.nonLazyAttr());
    testEqual(2, node.nonLazyAttr());
	}
}
