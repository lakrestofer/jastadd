import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		int i;
		B b0 = new B();
		B b1 = new B();
		B b2 = new B();
		A a = new A(new List(b0, b1, b2));
		
		B bSingle = new B();
		A aSingle = new A(new List(bSingle));
		
		testSame(b0, a.getB(0));
		testSame(b1, a.getB(1));
		testSame(b2, a.getB(2));
		
		testSame(bSingle, aSingle.getB(0));
	}
}
