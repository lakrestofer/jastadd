// Tests calling the original method in a refine statement using the refined keyword.
// .grammar: { X; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testEqual(15, new X().value());
	}
}
