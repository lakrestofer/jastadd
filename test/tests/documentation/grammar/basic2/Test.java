// .result=OUTPUT_PASS
public class Test {
	public static void main(String[] args) {
// DOC START
A a = new A(new B(), new C(new B()));
// DOC END
		printAst(a);
	}
	private static void printAst(ASTNode node) {
		System.out.println("digraph {");
		printNode(node, 0);
		System.out.println("}");
	}
	private static int printNode(ASTNode node, int nextId) {
		String name = node.getClass().getSimpleName();
		int id = nextId++;
		System.out.println("  n" + id + "[label=\"" + name + "\"];");
		for (int i = 0; i < node.getNumChild(); ++i) {
			ASTNode child = node.getChild(i);
			if (child == null) continue;
			int childId = nextId;
			nextId = printNode(child, childId);
			System.out.println("  n" + id + " -> n" + childId + ";");
		}
		return nextId;
	}
}
