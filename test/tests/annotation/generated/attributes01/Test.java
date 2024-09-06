// Tests generated annotations for attributes.
// .options: generateAnnotations=yes
import static runtime.Test.*;

import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

public class Test {
	public static void main(String[] args) throws Throwable {
		A a = new A();
		a.setB(new B("B"));
		
		// Test A
		Class<?> ac = A.class;
		ASTNodeAnnotation.Attribute attr;
		Method method;

		method = ac.getMethod("synA");
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.SYN, attr.kind());
		test(!attr.isNTA());
		test(!attr.isCircular());
		
		method = ac.getMethod("synParA", int.class);
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.SYN, attr.kind());
		test(!attr.isNTA());
		test(!attr.isCircular());
		
		method = ac.getMethod("ntaA");
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.SYN, attr.kind());
		test(attr.isNTA());
		test(!attr.isCircular());
		
		method = ac.getMethod("ntaParA", int.class);
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.SYN, attr.kind());
		test(attr.isNTA());
		test(!attr.isCircular());

		method = ac.getMethod("circularA");
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.SYN, attr.kind());
		test(!attr.isNTA());
		test(attr.isCircular());
		
		method = ac.getMethod("circularParA", int.class);
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.SYN, attr.kind());
		test(!attr.isNTA());
		test(attr.isCircular());

		method = ac.getMethod("collA");
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.COLL, attr.kind());
		test(!attr.isNTA());
		test(!attr.isCircular());
		
		// Test B
		Class<?> bc = B.class;
		method = bc.getMethod("inhB");
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.INH, attr.kind());
		test(!attr.isNTA());
		test(!attr.isCircular());
		
		method = bc.getMethod("inhParB", int.class);
		testSourceAnnotation(method);
		attr = getAnnotation(method);
		testEqual(ASTNodeAnnotation.Kind.INH, attr.kind());
		test(!attr.isNTA());
		test(!attr.isCircular());
	}
	
	public static ASTNodeAnnotation.Attribute getAnnotation(Method m) {
		test(m.isAnnotationPresent(ASTNodeAnnotation.Attribute.class));
		return m.getAnnotation(ASTNodeAnnotation.Attribute.class);
	}

	public static void testSourceAnnotation(Method m) {
		test(m.isAnnotationPresent(ASTNodeAnnotation.Source.class));
		ASTNodeAnnotation.Source source = m.getAnnotation(ASTNodeAnnotation.Source.class);
		testEqual("Test", source.aspect());
		test(source.declaredAt().length() > 0);
	}
}
