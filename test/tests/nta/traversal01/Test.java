import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A(new B());
		
		testEqual(1, a.getNumChild());
		for (int i = 0; i < a.getNumChild(); ++i) {
			testNotSame(a.getNtaB(), a.getChild(i));
		}
	}
}
