// Tests generated annotations for tokens.
// .options: generateAnnotations=yes
import static runtime.Test.*;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

public class Test {
	public static void main(String[] args) throws Throwable {
		A a = new A();
		a.setID("n00b");
		a.setI(1337);
		a.setB(new B());
		
		for (Method m: a.getClass().getDeclaredMethods()) {
			Annotation annotations[] = m.getDeclaredAnnotations();
			test(annotations.length <= 1);
			if (annotations.length == 1) {
				ASTNodeAnnotation.Token token = m.getAnnotation(ASTNodeAnnotation.Token.class);
				if (token != null) {
					if (token.name().equals("ID")) {
						testEquals(a.getID(), m.invoke(a, null));
					} else if (token.name().equals("I")) {
						testEquals(a.getI(), m.invoke(a, null));
					} else if (token.name().equals("B")) {
						testSame(a.getB(), m.invoke(a, null));
					}
				}
			}
		}
	}
}
