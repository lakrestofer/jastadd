import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node n1 = new Node(new X(), new X(), new X());

		testEqual(3, n1.getA().value());
		testEqual(0, n1.getB().value());
		testEqual(7, n1.getC().value());

		SubNode n2 = new SubNode(new X(), new X(), new X());
		
		testEqual(-1, n2.getA().value());
		testEqual(-1, n2.getB().value());
		testEqual(-1, n2.getC().value());
	}
}
