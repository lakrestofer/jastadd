// Tests the noStatic flag, which places the reference to the state object
// in the root node as ordinary field instead of using a static field.
// .grammar: { A ::= B; B; }
// .options=rewrite staticState=no
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A(new B());
		B b = a.getB();
		b.state();
		
		testNotNull(a.testGetState());
		testSame(a.testGetState(), b.testGetState());
	}
}
