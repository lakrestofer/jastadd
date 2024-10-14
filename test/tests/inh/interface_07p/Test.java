import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    SpecialFather father = new SpecialFather(new Son());
    testFalse(father.getSon().attr());
  }
}
