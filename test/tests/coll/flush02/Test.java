// .options: flush
import static runtime.Test.*;

public class Test {

	public static void main(String[] args) {
		A a = new A();
		B b1 = new B("1");
		B b2 = new B("2");
		a.addB(b1);
		a.addB(b2);
		
		testTrue(b1.pred().contains(b2));
		testTrue(b2.pred().contains(b1));
		testFalse(b1.pred().contains(b1));
		testFalse(b2.pred().contains(b2));
		
		// after flushCache the collection attribute should be recomputed
		a.flushCache();
		b1.flushCache();
		b2.flushCache();
		
		B b3 = new B("3");
		a.addB(b3);
		testTrue(b1.pred().contains(b3));
		testFalse(b1.pred().contains(b2));
		testFalse(b1.pred().contains(b1));
		testTrue(b2.pred().contains(b1));
		testFalse(b2.pred().contains(b2));
		testFalse(b2.pred().contains(b3));
		testTrue(b3.pred().contains(b2));
		testFalse(b3.pred().contains(b1));
		testFalse(b3.pred().contains(b3));
	}
}
