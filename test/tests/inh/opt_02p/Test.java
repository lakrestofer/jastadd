import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testFalse(new X().getXOpt().childOfY());
		testTrue(new Y().getXOpt().childOfY());
	}
}
