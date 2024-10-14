// Tests that the generated method List.addAll() works as expected
// when passed an ArrayList.
// .grammar: { A ::= B*; B; C : B; }
import static runtime.Test.*;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {		
		// Add empty ArrayList to empty List.
		A a = new A(new List().addAll(new ArrayList<B>()));
		testTrue(a.getBList().getNumChild()==0);
		
		// Add non-empty ArrayList to empty List.
		ArrayList<B> bs = new ArrayList<B>();
		bs.add(new B());
		bs.add(new B());
		a.getBList().addAll(bs);
		testSame(bs.get(0), a.getB(0));
		testSame(bs.get(1), a.getB(1));
		
		// Add non-empty ArrayList to non-empty List.
		ArrayList<B> moreBs = new ArrayList<B>();
		moreBs.add(new B());
		moreBs.add(new B());
		a.getBList().addAll(moreBs);
		testSame(bs.get(0), a.getB(0));
		testSame(bs.get(1), a.getB(1));
		testSame(moreBs.get(0), a.getB(2));
		testSame(moreBs.get(1), a.getB(3));	
	}
}
