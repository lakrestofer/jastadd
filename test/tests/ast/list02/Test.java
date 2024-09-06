import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		B b0 = new B();
		B b1 = new B();
		B b2 = new B();
		B b3 = new B();
		List bList = new List().add(b0).add(b1);
		A a = new A(bList);

		testEqual(2, a.getNumB());
		
		a.getBList().addChild(b2);
		a.getBList().addChild(b3);
		
		testEqual(4, a.getNumB());
		
		testSame(b0, a.getB(0));
		testSame(b1, a.getB(1));
		testSame(b2, a.getB(2));
		testSame(b3, a.getB(3));
	}
}
