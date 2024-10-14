import static runtime.Test.*;

public class Test {
    public static void main(String args[]) {
        A a = new A();

        B b1 = a.b();
        testEqual(0, b1.getN());
        testTrue(b1.getS().isEmpty());
        
        B b2 = a.b(1);
        testEqual(1, b2.getN());
        testTrue(b2.getS().isEmpty());
        
        B b3 = a.b(2, "hello world");
        testEqual(2, b3.getN());
        testEqual("hello world", b3.getS());
    }
}
