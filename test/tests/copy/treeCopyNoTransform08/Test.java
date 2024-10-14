import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		AnonymousDecl decl = new AnonymousDecl(new Modifiers(new List()), "Object", new List());
		decl = decl.treeCopyNoTransform();
		decl.getImplementsListNoTransform();
	}
}
