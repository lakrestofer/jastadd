// Interface methods can be refined, even if the parameter modifiers are different.
// .grammar: { Node; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Interface node = new Node();
		testNull(node.returnSomething(new ASTNode()));
	}
}
