import java.util.*;
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node(new NamedNode("non-nta"));

		Collection<String> names = node.childNames();
		testTrue(names.contains("non-nta"));
		testFalse(names.contains("nta"));
	}
}
