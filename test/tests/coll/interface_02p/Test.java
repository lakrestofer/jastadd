// Test multi-level inheritance of collection attribute through interfaces.
// https://bitbucket.org/jastadd/jastadd2/issue/204/multi-level-interface-inheritance-of
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();
		a.addB(new B("1"));
		a.addB(new B("2"));
		
		testTrue(a.set().contains("1"));
		testTrue(a.set().contains("2"));
	}
}
