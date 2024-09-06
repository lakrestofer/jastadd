import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a1 = new A();
		A a2 = new A();
		Node node = new Node(new List().add(a1).add(a2));

		testEqual(-1, a1.value());

		testEqual(0, a2.value());
	}
}
