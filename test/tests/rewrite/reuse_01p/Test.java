// Tests resuse of nodes during evaluation of rewrite using CNTA algorithm.
// .options=rewrite=cnta
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Root root = new Root(new A(new B()));
		A a = root.getA();
		A aRef = a.getB().a();
		testEqual(a, aRef);		
	}
}
