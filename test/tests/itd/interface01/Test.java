import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Object o = new A();
		
		test(o instanceof B);
	}
}
