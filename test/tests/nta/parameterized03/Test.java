// A parameterized NTA can have a `null` value.
import static runtime.Test.*;

public class Test {
    public static void main(String args[]) {
        A a = new A();

        testNull(a.b(0));
        testNotNull(a.b(1));
        testNotNull(a.b(-1));
        testNotNull(a.b(2));
    }
}
