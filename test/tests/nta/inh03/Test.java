import static runtime.Test.*;

public class Test {
    public static void main(String args[]) {
        A a = new A(new B());    
        testEqual(20, a.getB().attr());
        testEqual(10, a.b().attr());
    }
}
