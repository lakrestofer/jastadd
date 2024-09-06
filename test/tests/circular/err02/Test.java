// Intertype method and field declarations get @Source annotations
// .grammar: {Node;}
// .options: cache=none
import static runtime.Test.*;

public class Test {
    private static Node node;

    public static void startTest() {
    }

    public static void testCombinations(int depth, String path) {
	if (depth <= 0) {
	    return;
	}

	for (int i = 0; i < 6; ++i) {
	    String expectedException = null;
	    String npath = path + i;

	    switch (i) {
	    case 2:
	    case 4:
		expectedException = "Circular definition of attribute Node.badUncache().";
		break;
	    case 3:
	    case 5:
		expectedException = "Circular definition of attribute Node.badCache().";
		break;
	    default:
		break;
	    }

	    try {
		switch (i) {
		case 0: testEqual(100, node.goodUncache());
		    break;
		case 1: testEqual(100, node.goodCache());
		    break;

		case 2: node.badUncache();
		    break;
		case 3: node.badCache();
		    break;

		case 4: System.err.println("OBS: " + node.uglyUncache());
		    break;
		case 5: System.err.println("OBS: " + node.uglyCache());
		    break;
		}
	    } catch (RuntimeException exn) {
		testEqual(expectedException, exn.getMessage());
		expectedException = null;
	    }
	    testNull(expectedException);

	    // recurse
	    testCombinations(depth - 1, npath);
	}
    }


    public static void main(String[] args) {
	node = new Node();
	testCombinations(3, "");
    }
}
