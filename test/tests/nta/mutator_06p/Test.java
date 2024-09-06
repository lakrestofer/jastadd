// Test that an NTA List value can be mutated.
// .result=COMPILE_PASS
// .tags=nta,codegen
// .grammar: { A ::= /B*/; B; }
public class Test {
  public static void main(String[] args) {
    A a = new A();
		a.addB(new B()); // Adding to the NTA list value does not throw an exception.
		a.setB(new B(), 0); // Adding to the NTA list value does not throw an exception.
  }
}
