// Intertype class, interface, enum declarations get @Source annotations
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
	testSourceAnnotation("enum TEnum", "AspectA", "Test.jadd:2",
			     TEnum.class.getAnnotation(ASTNodeAnnotation.Source.class));
	testSourceAnnotation("interface TInterface", "AspectA", "Test.jadd:3",
			     TInterface.class.getAnnotation(ASTNodeAnnotation.Source.class));
	testSourceAnnotation("class TClass", "AspectA", "Test.jadd:4",
			     TClass.class.getAnnotation(ASTNodeAnnotation.Source.class));
    }
}
