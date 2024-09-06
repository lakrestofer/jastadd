import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		X x0 = new X();
		X x1 = new X();
		Y y = new Y();
		Z z = new Z();
		W w = new W();
		
		SubNode n = new SubNode();
		
		n.setX0(x0);
		n.setX1(x1);
		n.setY(y);
		n.setZ(z);
		n.setW(w);
		
/* <FUTURE> order of children in SubNode: X0 Y Z W X1  */
/*		test(n.getChildNoTransform(0) == x0);
		test(n.getChildNoTransform(1) == y);
		test(n.getChildNoTransform(2) == z);
		test(n.getChildNoTransform(3) == w);
		test(n.getChildNoTransform(4) == x1);*/
/* <CURRENT> order of children in SubNode: Z W Y X0 X1  */
		testSame(z, n.getChildNoTransform(0));
		testSame(w, n.getChildNoTransform(1));
		testSame(y, n.getChildNoTransform(2));
		testSame(x0, n.getChildNoTransform(3));
		testSame(x1, n.getChildNoTransform(4));
	}
}
