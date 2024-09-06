// .COMPILE_PASS
import static runtime.Test.*;

import java.util.ArrayList;

public class Test {
	public static final void main(String[] args) {
		Answer answer1 = new Yes();
		Answer answer2 = new No();
		testFalse(answer1.isSame(answer2));
	}
}
