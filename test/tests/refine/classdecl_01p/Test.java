// Tests refining a method in an aspect-declared class.
// .grammar: { Node; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testFalse(new Node().find("red"));
		testTrue(new Node().find("blue"));
	}
}
