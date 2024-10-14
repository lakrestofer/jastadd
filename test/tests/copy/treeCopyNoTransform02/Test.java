// .result=EXEC_PASSED
// .options=--beaver
// .classpath=lib/beaver-rt.jar
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		Node copy = node.treeCopyNoTransform();

		// the value field must be set correctly for the copy
		testNotSame(copy.value, node);
	}
}
