// .options=rewrite=cnta
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		B initChild = new B();
		Program p = new Program(initChild);
		A rewrittenChild = p.getA();
		
		testEqual(p, initChild.getParent());
		testEqual(p, rewrittenChild.getParent());
		
	}
}
