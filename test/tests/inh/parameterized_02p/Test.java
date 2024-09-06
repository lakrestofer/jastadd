import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Decl d1 = new Decl("int", "d");
		Use u1 = new Use("d");
		Decl d2 = new Decl("string", "d");
		Use u2 = new Use("d");
		Program p = new Program();
		p.addExpr(d1);
		p.addExpr(u1);
		p.addExpr(d2);
		p.addExpr(u2);

		testEqual("int", u1.decl().getType());

		testEqual("string", u2.decl().getType());
	}
}
