// An .ast method can be refined via the "" aspect.
// An refinement of an "" method may use the "refined()" construct.
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		// Refinement of syn equations that uses refined() to invoke old implementation
		testFalse(new X(true).getLazy());
	}
}
