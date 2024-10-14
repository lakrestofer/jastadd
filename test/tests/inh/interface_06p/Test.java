import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Father father = new Father(new Son(), new Son());
    testTrue(father.getFirst().attr());
    testFalse(father.getSecond().attr());
  }
}
