import static runtime.Test.*;

public class Test {
	public static final void main(String[] args) {
		B left = new B();
		B right = new B();
		A a = new A(left, right);
		
		testSame(left, a.getLeft());
		testSame(right, a.getRight());
	}
}