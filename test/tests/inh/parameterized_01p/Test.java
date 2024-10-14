import static runtime.Test.*;

public class Test {
	public static final void main(String[] arg) {
		Root root = new Root();
		root.setNode(new Node());
		root.getNode().setNode(new Node());

		testEqual(26, root.getNode().inhAttr(13));

		testEqual(39, root.getNode().getNode().inhAttr(13));
	}
}
