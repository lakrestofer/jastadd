// Circular attributes can be parameterized.
//
// .options: cacheCycle=no
public class Test {  
  public static void main(String[] args) {
    B b = new B();
    A a = new A(b);

    if (a.x("a") == true)
      System.out.println("PASS");
    else
      System.out.println("FAIL");

    if (b.y("a") == true)
      System.out.println("PASS");
    else
      System.out.println("FAIL");

    if (a.x("b") == true)
      System.out.println("PASS");
    else
      System.out.println("FAIL");

    if (b.y("b") == true)
      System.out.println("PASS");
    else
      System.out.println("FAIL");
  }
}
