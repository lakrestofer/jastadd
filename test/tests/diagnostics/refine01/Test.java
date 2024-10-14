// .result=JASTADD_ERR_OUTPUT
import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		X x = new X();
		testEqual(13, x.value());
	}
}
