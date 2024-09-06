// .result=EXEC_PASSED
// .options=rewrite
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		testNull(node.getChildNoTransform(0));
		testNull(node.getChildNoTransform(1));
		test(node.getChildNoTransform(2) instanceof Opt);
		test(node.getChildNoTransform(3) instanceof List);
	}
}
