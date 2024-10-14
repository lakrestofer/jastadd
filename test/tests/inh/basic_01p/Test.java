import static runtime.Test.*;

public class Test {
	public static final void main(String[] arg) {
		Root root = new Root();
		root.setNode(new Node());
		root.getNode().setNode(new Node());

		testEqual(-1, root.getNode().inhAttr());

		testEqual(0, root.getNode().getNode().inhAttr());
	}
}
