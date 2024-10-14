// .options=jjtree grammar=TestParser
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Program program = new Program();
		try {
			program.addX(new X());
		} catch (NullPointerException e) {
			error("List child not properly initialized!");
		}
	}
}