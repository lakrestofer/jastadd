// Tests the noStatic flag, which places the reference to the state object
// in the root node as ordinary field instead of using a static field.
// .grammar: { A ::= B; B; }
// .options=rewrite staticState=no
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		a.state();
		b.state(); // 'b' is not in the same tree as 'a'
		a.setB(b);
		
		testNotNull(a.testGetState());
		testNotNull(b.testGetState());
		testNotSame(a.testGetState(), b.testGetState());
	}
}
