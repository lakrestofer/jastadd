// Test an NTA setter method throws an exception.
// .tags=nta,codegen
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();
		a.setB(new B());
		B b = new B();
		try {
			b.setB(new B());// throws java.lang.Error
			// exception not thrown
			System.err.println("Expected exception!");
			System.exit(1);
		} catch (Error e) {
			testEqual("Can not replace NTA child B in B!", e.getMessage());
		}
	}
}
