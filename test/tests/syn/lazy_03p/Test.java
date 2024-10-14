// It is possible to make a single attribute equation lazy if the `syn T A.x(...)`
// syntax is used. For example:
// 
//    // the syn attribute attr is only cached in B
//    syn      Object A.attr() = new Object();
//    syn lazy Object B.attr() = new Object();
//    syn      Object C.attr() = new Object();
//    eq              D.attr() = new Object();	
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		C c = new C();
		D d = new D();
		
		test("A.attr should not be cached", !a.isAttrCached());
		test("B.attr should be cached",      b.isAttrCached());
		test("C.attr should not be cached", !c.isAttrCached());
		test("D.attr should not be cached", !d.isAttrCached());
	}
}
