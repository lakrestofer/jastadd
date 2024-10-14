import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testFalse(new X().getXList().childOfY());
		testTrue(new Y().getXList().childOfY());
	}
}
