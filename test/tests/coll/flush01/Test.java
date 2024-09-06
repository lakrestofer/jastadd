// .options: flush
import static runtime.Test.*;

public class Test {

	public static void main(String[] args) {
		A a = new A();
		a.addB(new B("1"));
		a.addB(new B("2"));
		a.addB(new B("3"));
		
		testTrue(a.set().contains("1"));
		testTrue(a.set().contains("2"));
		testTrue(a.set().contains("3"));
		testFalse(a.set().contains("4"));
		
		// After flushCache the collection attribute should be recomputed.
		a.flushCache();
		a.addB(new B("4"));
		testTrue(a.set().contains("1"));
		testTrue(a.set().contains("2"));
		testTrue(a.set().contains("3"));
		testTrue(a.set().contains("4"));
	}
}
