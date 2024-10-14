// .options=rewrite | rewrite=circularNTA
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Program p = new Program(new B());
		testEqual(-1, p.getA().value());
	}
}
