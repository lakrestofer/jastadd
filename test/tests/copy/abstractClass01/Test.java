import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Root r = new Root(new B());
		A a = r.getA().fullCopy();
		a = r.getA().treeCopyNoTransform();
		a = r.getA().treeCopy();
	}
}
