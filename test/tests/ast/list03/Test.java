// List nodes can be traversed using enhanced for and the generated getXList() method.
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		int i;
		A a = new A(new List().add(new B()).add(new B()));
		
		// Enhanced for using getBList()
		i = 0;
		for (B node : a.getBList()) {
			testSame(a.getB(i), node);
			i += 1;
		}
		
		// Enhanced for using getBs()
		i = 0;
		for (B node : a.getBs()) {
			testSame(a.getB(i), node);
			i += 1;
		}
	}
}
