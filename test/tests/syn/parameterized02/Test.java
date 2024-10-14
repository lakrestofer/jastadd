import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		SubNode node = new SubNode();
		testTrue(node.attr(""));
	}
}
