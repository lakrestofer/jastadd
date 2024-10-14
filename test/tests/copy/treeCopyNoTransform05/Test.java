import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();
		C c = new C();
		a.setC(c);
		a = (A) a.treeCopyNoTransform();
		testNotNull(a.getC());
	}
}
