// Synthesized and inherited attributes can be declared circular, even if they
// are actually not circular.
//
// .result=COMPILE_PASSED
public class Test {
  public static void main(String[] args) {
    A a = new A(new B());
    a.a();
    a.b();
    a.c();
    a.d();
    a.e();
    a.f();
    a.getB().g();
    a.getB().h();
  }
}
