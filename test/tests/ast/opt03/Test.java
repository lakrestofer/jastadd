import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		// an uninitialized Opt node has array size 0 (children == null)
		testEqual("child array is oversized", 0, (new Opt()).childArraySize());
		
		// an initialized Opt node has array size 1
		testEqual("child array is oversized", 1, (new Opt(new A())).childArraySize());
	}
}
