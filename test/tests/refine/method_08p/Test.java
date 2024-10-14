// Test multi-level refinement.
// .grammar: { X; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testEqual(7, new X().value());
	}
}
