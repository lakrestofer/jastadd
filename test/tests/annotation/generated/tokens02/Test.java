// Test that annotations are not generated for tokens when annotation generation is turned off.
// See https://bitbucket.org/jastadd/jastadd2/issues/312/token-annotations-are-generated-when
// .grammar: { A ::= <ID> <I:int> <A:A>; }
// .options: generateAnnotations=false
import static runtime.Test.*;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

public class Test {
  public static void main(String[] args) throws Throwable {
    for (Method m: A.class.getDeclaredMethods()) {
      if (m.getName().equals("getID")) {
        for (Annotation annot : m.getAnnotations()) {
          testThat(annot.annotationType().getName()).notEqual("ASTNodeAnnotation$Token");
        }
      }
    }
  }
}
