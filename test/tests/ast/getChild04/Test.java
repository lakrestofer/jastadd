import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    testInsert();
    testRemove();
  }

  private static void testInsert() {
    Node n = new Node();
    for (int i = 0; i < 10; ++i)
      n.addNode(new Node());
    n.getNodeList().insertChild(new Node(), 5);
    testEquals(5, n.getNode(5).childIndex());
    testEquals(6, n.getNode(6).childIndex());
  }

  private static void testRemove() {
    Node n = new Node();
    for (int i = 0; i < 10; ++i)
      n.addNode(new Node());
    n.getNodeList().removeChild(6);
    testEquals(5, n.getNode(5).childIndex());
    testEquals(6, n.getNode(6).childIndex());
  }
}
