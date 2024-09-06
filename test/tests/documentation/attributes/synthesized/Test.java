import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
// DOC START
Stmt stmt = new Stmt(new Add(new Num("3"),
			new Add(new Num("101"), new Num("6"))));
int maxDepth = stmt.maxDepth();
// DOC END

		printAst(stmt);
		testEqual(4, maxDepth);
	}
	private static void printAst(ASTNode node) {
		System.out.println("digraph {");
		printNode(node, 0);
		System.out.println("}");
	}
	private static int printNode(ASTNode node, int nextId) {
		String name = node.getClass().getSimpleName();
		int id = nextId++;
		System.out.println("  n" + id + "[label=\"" + name + " (maxDepth=" + node.maxDepth() + ")\"];");
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
