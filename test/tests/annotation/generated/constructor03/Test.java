// Testing that the annotation for the constuctor is correct.
// .options: generateAnnotations=yes
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor; 
import java.lang.reflect.Constructor; 
import static runtime.Test.*;
public class Test {
    public static void main(String[] args) throws  NoSuchMethodException, SecurityException {
        Constructor<A> constructor = A.class.getConstructor(B.class,int.class,Opt.class,List.class);
        ASTNodeAnnotation.Constructor attr = constructor.getAnnotation(ASTNodeAnnotation.Constructor.class);
        isEqualArray(new String[]{"B","c","d","hej"}, attr.name());
        isEqualArray(new String[]{"B","int","Opt<D>","List<E>"}, attr.type());
        isEqualArray(new String[]{"Child","Token","Opt","List"}, attr.kind());
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
