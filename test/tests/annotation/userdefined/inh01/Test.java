// .result=JASTADD_ERR_OUTPUT
import static runtime.Test.*;

public class Test {
	public static final void main(String[] arg) {
		Root root = new Root();
		root.setNode(new Node());
		root.getNode().setNode(new Node());

		root.getNode().inhAttr();
		root.getNode().getNode().inhAttr();
	}
}
