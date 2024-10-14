// Tests generated annotations for children.
// .options: generateAnnotations=yes
import static runtime.Test.*;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

public class Test {
	public static void main(String[] args) throws Throwable {
		A a = new A();
		a.setB1(new B());
		a.addB2(new B());
		a.setB3(new B());
		
		for (Method m: a.getClass().getDeclaredMethods()) {
			if (m.isAnnotationPresent(ASTNodeAnnotation.Child.class)) {
				testSame(a.getB1(), m.invoke(a, null));
			} else if (m.isAnnotationPresent(ASTNodeAnnotation.ListChild.class)) {
				testSame(a.getB2List(), m.invoke(a, null));
			} else if (m.isAnnotationPresent(ASTNodeAnnotation.OptChild.class)) {
				testSame(a.getB3Opt(), m.invoke(a, null));
			}
		}
	}
}
