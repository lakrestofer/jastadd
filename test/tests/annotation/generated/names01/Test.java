// Tests generated annotation element "name".
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
			Annotation annotations[] = m.getDeclaredAnnotations();
			test(annotations.length <= 1);
			if (annotations.length == 1) {
				ASTNodeAnnotation.Child child = m.getAnnotation(ASTNodeAnnotation.Child.class);
				ASTNodeAnnotation.ListChild listChild = m.getAnnotation(ASTNodeAnnotation.ListChild.class);
				ASTNodeAnnotation.OptChild optChild = m.getAnnotation(ASTNodeAnnotation.OptChild.class);

				if (child != null) {
					testEquals("B1", child.name());
				} else if (listChild != null) {
					testEquals("B2", listChild.name());
				} else if (optChild != null) {
					testEquals("B3", optChild.name());
				}
			}
		}
	}
}
