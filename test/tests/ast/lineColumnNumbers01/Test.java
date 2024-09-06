// .options=lineColumnNumbers
import static runtime.Test.*;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		final int startLine = 1;
		final short startColumn = 2;
		final int endLine = 3;
		final short endColumn = 4;
		
		A a = new A();
		a.setStart(startLine, startColumn);
		a.setEnd(endLine, endColumn);
		
		testEquals(startLine, a.getStartLine());
		testEquals(startColumn, a.getStartColumn());
		testEquals(endLine, a.getEndLine());
		testEquals(endColumn, a.getEndColumn());
	}
}
