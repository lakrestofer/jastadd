// Tests refining a simple inter-type declaration.
// .grammar: { X; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		X x = new X();
		testEqual(13, x.value());
	}
}
