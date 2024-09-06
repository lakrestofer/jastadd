import static runtime.Test.*;

public class Test {
	public static final void main(String[] arg) {
		Root root = new Root();
		root.setNode(new Node());
		root.getNode().setNode(new Node());

		testSame(root, root.getNode().getNode().root());
	}
}
