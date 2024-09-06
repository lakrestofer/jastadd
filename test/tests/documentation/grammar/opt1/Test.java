// .result=OUTPUT_PASS
public class Test {
	public static void main(String[] args) {
// DOC START
A a1 = new A(new Opt(new B()));
A a2 = new A(new Opt());
// DOC END
		System.out.println("digraph {");
		int nextId = printNode(a1, " \\\"a1\\\"", 0);
		printNode(a2, " \\\"a2\\\"", nextId);
		System.out.println("}");
	}
	private static int printNode(ASTNode node, String comment, int nextId) {
		String name = node.getClass().getSimpleName();
		int id = nextId++;
		System.out.println("  n" + id + "[label=\"" + name + comment + "\"];");
		for (int i = 0; i < node.getNumChild(); ++i) {
			ASTNode child = node.getChild(i);
			if (child == null) continue;
			int childId = nextId;
			nextId = printNode(child, "", childId);
			System.out.println("  n" + id + " -> n" + childId + ";");
		}
		return nextId;
	}
}
