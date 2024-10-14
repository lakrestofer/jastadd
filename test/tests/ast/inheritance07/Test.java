import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		X x0 = new X();
		X x1 = new X();
		Y y = new Y();
		Z z = new Z();
		W w = new W();
		
		Node n = new SubSubNode(x0, y, w, z, x1);
		
/* <FUTURE> order of children in SubSubNode: X0 Y Z W X1  */
/*		test(n.getChildNoTransform(0) == x0);
		test(n.getChildNoTransform(1) == y);
		test(n.getChildNoTransform(2) == z);
		test(n.getChildNoTransform(3) == w);
		test(n.getChildNoTransform(4) == x1);*/
/* <CURRENT> order of children in SubSubNode: X0 Y W Z X1  */
		test(n.getChildNoTransform(0) == x0);
		test(n.getChildNoTransform(1) == y);
		test(n.getChildNoTransform(2) == w);
		test(n.getChildNoTransform(3) == z);
		test(n.getChildNoTransform(4) == x1);
	}
}
