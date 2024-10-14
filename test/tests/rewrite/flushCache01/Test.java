// .options=rewrite flush=full
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();
		a.b(-1);
		a.b(0);
		a.b(2);
		a.b(29968123);
		
		a.flushCache();
		test("The internal NTA value list should be set to null during flushCache",
				a.valueListFlushed());
	}
}
