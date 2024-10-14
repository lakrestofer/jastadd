import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Package node = new Package();
		Program program = new Program();
		program.addPackage(node);
		
		testEqual(0, program.lazyEvalCount);
		testEqual(0, program.nonLazyEvalCount);
		
		for (int i = 0; i < 3; ++i) {
			node.lazyAttr();
			node.nonLazyAttr();
			testEqual(1, program.lazyEvalCount);
			testEqual(1+i, program.nonLazyEvalCount);
		}
	}
}
