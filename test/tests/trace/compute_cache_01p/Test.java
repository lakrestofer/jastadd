// Tests the flag --tracing=compute,cache.
// .options: tracing=compute,cache rewrite
// .source: 1.8
import static runtime.Test.*;

import java.io.PrintWriter;

public class Test {
  public static void main(String[] args) {
    Node node = new Node(new B());

    node.trace().setReceiver(new ASTState.Trace.Receiver() {
      public void accept(ASTState.Trace.Event event, ASTNode node,
          String attribute, Object params, Object value) {
        switch (event) {
          case COMPUTE_BEGIN:
          case COMPUTE_END:
          case CACHE_WRITE:
          case CACHE_ABORT:
          case CACHE_READ:
            break;
          default:
            fail("Other trace event than compute|cache: " + event);
        }
      }
    });

    node.getA();
    testEquals(1, node.a1());
    testEquals(2, node.a2(0));
    testEquals(3, node.a3(0, 0));
    testEquals(4, node.a4());
    testEquals(5, node.a5(0));
    testEquals(6, node.a6(0, 0));
    testEquals(7, node.a7());
    testEquals(8, node.a8(0));
    testEquals(9, node.a9(0, 0));
  }
}
