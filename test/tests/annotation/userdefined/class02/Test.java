import java.lang.reflect.Method;

import static runtime.Test.*;

public class Test {
	public static void main(String[] args) throws Throwable {
		Method m = MyClass.class.getMethod("sillyMethod");
		test("annotation not found!", m.isAnnotationPresent(Silly.class));
		m = MyClass.class.getMethod("seriousMethod");
		test("unexpected annotation!", !m.isAnnotationPresent(Silly.class));
	}
}
