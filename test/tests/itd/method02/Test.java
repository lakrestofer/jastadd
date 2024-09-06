import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		
		testEqual("overloaded1", node.overloaded());
		testEqual("overloaded2", node.overloaded(0));
		testEqual("overloaded3", node.overloaded(""));
	}
}
