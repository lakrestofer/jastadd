import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();
		Node node = new Node(a);
		testEqual(13, a.value());
	}
}
