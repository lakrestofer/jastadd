import java.lang.reflect.Method;

import static runtime.Test.*;

public class Test {
	public static void main(String[] args) throws Throwable {
		A a = new A();
		Method m = a.getClass().getMethod("name");
		test("annotation not found!", m.isAnnotationPresent(Silly.class));
	}
}
