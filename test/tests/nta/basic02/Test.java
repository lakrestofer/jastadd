import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		NodeA a = new NodeA();
		NodeB b = new NodeB();
		testSame(a.getA(), a.theA);
		testSame(b.getNtaB(), b.theB);
	}
}
