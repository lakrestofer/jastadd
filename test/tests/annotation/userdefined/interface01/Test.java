import java.lang.reflect.Method;

import static runtime.Test.*;

public class Test {
	public static void main(String[] args) throws Throwable {
		Class<?> klass = MyInterface.class;
		test("annotation not found!", klass.isAnnotationPresent(Silly.class));
	}
}
