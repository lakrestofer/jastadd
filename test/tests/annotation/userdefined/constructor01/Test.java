import java.lang.reflect.Method;

import static runtime.Test.*;
import java.lang.reflect.Constructor;

public class Test {
	public static void main(String[] args) throws Throwable {
		Constructor<A> con = A.class.getConstructor(int.class);
		test("annotation not found!", con.isAnnotationPresent(Silly.class));
	}
}
