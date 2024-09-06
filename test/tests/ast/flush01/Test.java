// .options: flush
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
	Size aVal = new Size(1);
	boolean[] bVal = new boolean[2];
	C cVal = new C();
	String dVal = new String("abc"); // create -new- object
	Node node = new Node(aVal, bVal, cVal, dVal);
	node.flushCache();
	testSame(aVal, node.getA());
	testSame(bVal, node.getB());
	testSame(cVal, node.getC());
	testSame(dVal, node.getD());
  }
}
