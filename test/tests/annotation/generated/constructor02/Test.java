// Testing that the annotation for the constuctor is correct.
// .options: generateAnnotations=yes
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor; 
import java.lang.reflect.Constructor; 
import static runtime.Test.*;
public class Test {
    public static void main(String[] args) throws  NoSuchMethodException, SecurityException {
        Constructor<A> constructor = A.class.getConstructor(B.class);
        ASTNodeAnnotation.Constructor attr = constructor.getAnnotation(ASTNodeAnnotation.Constructor.class);
        isEqualArray(new String[]{"b"}, attr.name());
        isEqualArray(new String[]{"B"}, attr.type());
        isEqualArray(new String[]{"Child"}, attr.kind());
        
        Constructor<B> constructorB = B.class.getConstructor();
        ASTNodeAnnotation.Constructor attrB = constructorB.getAnnotation(ASTNodeAnnotation.Constructor.class);
        testNull(attrB);
    }

    public static void isEqualArray(Object[] a1 , Object[] a2){
        if(a1.length != a2.length){
            fail("not same length");
        }
        for(int i = 0; i < a1.length; i++){
            testEquals(a1[i],a2[i]);
        }
    }
}
