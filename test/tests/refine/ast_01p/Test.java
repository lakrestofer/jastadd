// Tests refining an auto-generated AST method.
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testTrue(new X(false).getFlag());
		testFalse(new X(true).getFlag());
	}
}
