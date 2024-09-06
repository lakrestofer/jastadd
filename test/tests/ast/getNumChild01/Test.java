// Tests numChildren and addChild
// .grammar: { Node; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		List list = new List();

		testEqual(0, list.numChildren());
		
		list.addChild(new Node());
		list.addChild(new Node());

		testEqual(2, list.numChildren());
	}
}
