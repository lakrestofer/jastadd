import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A("");
		testTrue(a.customConstructor);
	}
}
