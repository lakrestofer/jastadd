// Nonterminals do not contribute to the child count.
// .grammar: { A ::= B /B1:B/; B; }
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		testEquals(1, new A().getNumChild());
	}
}
