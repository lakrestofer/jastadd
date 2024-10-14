import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		X x0 = new X();
		X x1 = new X();
		Z z = new Z();
		W w = new W();
		
		SubNode n = new SubNode(z, w, x0, x1);
		
		Y y = n.getY();
		
/* <FUTURE> order of children in SubNode: X0 Y Z W X1 */
/*		test(n.getChildNoTransform(0) == x0);
		test(n.getChildNoTransform(1) == y);
		test(n.getChildNoTransform(2) == z);
		test(n.getChildNoTransform(3) == w);
		test(n.getChildNoTransform(4) == x1);*/
/* <CURRENT> order of children in SubNode: Z W X0 X1 Y (NTA PLACED LAST) */
		test(n.getChildNoTransform(0) == z);
		test(n.getChildNoTransform(1) == w);
		test(n.getChildNoTransform(2) == x0);
		test(n.getChildNoTransform(3) == x1);
		test(n.getChildNoTransform(4) == y);
	}
}
