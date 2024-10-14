// .options: flush
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
	Node node = new Node();
	C cVal1 = node.getC();
	C cVal2 = node.c();
	node.flushCache();
	testNotSame(cVal1, node.getC());
	testNotSame(cVal2, node.c());
  }
}

