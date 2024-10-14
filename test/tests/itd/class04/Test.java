// Test declaring class as an inter-type declaration
// .result=COMPILE_PASS
class Test {
	A.B b = new A().new B();
}
