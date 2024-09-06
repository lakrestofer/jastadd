// Intertype method and field declarations get @Source annotations
// .grammar: {Node;}
// .options: generateAnnotations=yes
import static runtime.Test.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import static runtime.Test.*;

public class Test {
    static void testSourceAnnotation(String explanation, String aspect, String location,
				     Annotation annotation) {
	test(explanation + ": annotation exists", annotation != null);
	ASTNodeAnnotation.Source a = (ASTNodeAnnotation.Source) annotation;
	testEqual(explanation + ": correct aspect",
		  aspect, a.aspect());
	test(explanation + ": correct location",
	     a.declaredAt().endsWith(location));
    }

    public static final void main(String[] args) throws NoSuchMethodException, NoSuchFieldException, SecurityException {
	testSourceAnnotation("method Node.foo(int)", "AspectA", "Test.jadd:2",
			     Node.class.getMethod("foo").getAnnotation(ASTNodeAnnotation.Source.class));
	testSourceAnnotation("method Node.bar()", "AspectB", "Test.jadd:8",
			     Node.class.getMethod("bar").getAnnotation(ASTNodeAnnotation.Source.class));
	testSourceAnnotation("field Node.tfield()", "AspectA", "Test.jadd:3",
			     Node.class.getField("tfield").getAnnotation(ASTNodeAnnotation.Source.class));
	testSourceAnnotation("constructor Node.Node(int)", "AspectA", "Test.jadd:4",
			     Node.class.getConstructor(int.class).getAnnotation(ASTNodeAnnotation.Source.class));
    }
}
