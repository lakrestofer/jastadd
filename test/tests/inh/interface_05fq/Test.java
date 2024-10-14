// .result=JASTADD_ERR_OUTPUT
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Father father = new Father(new Son());
    testFalse(father.getSon().attr());
  }
}
