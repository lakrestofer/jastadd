import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    // Create a non-terminal attribute with an argument
    testEqual("hello1", new X().myY("hello1").getID());
    testEqual("hello2", new X().myY("hello2").getID());
  }
}
