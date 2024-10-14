// .options=rewrite
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		List list = new List();

		list.addChild(null);
		testNull(list.getChild(0));
	}
}
