import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    GrandFather gran = new GrandFather(new Father(new Son()));
    testFalse(gran.getFather().getSon().attr());
  }
}
