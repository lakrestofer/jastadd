import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node();
		
		testEqual(13, node.calc(0));

		node = new SubNode();
		testEqual(-1, node.calc(0));
	}
}
