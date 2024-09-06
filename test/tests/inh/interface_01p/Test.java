import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Father father = new Father(new Son());
    testTrue(father.getSon().attr());
  }
}
