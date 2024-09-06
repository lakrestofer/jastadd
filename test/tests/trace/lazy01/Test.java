// Tests tracing of lazy evaluation.
// .options: tracing
// .source: 1.8
import static runtime.Test.*;

import java.io.PrintWriter;

public class Test {
  static class EvalTracker implements ASTState.Trace.Receiver {
    public int numCacheWrite;
    public int numCacheRead;

    public void accept(ASTState.Trace.Event event, ASTNode node,
        String attribute, Object params, Object value) {
      switch (event) {
        case CACHE_WRITE:
          numCacheWrite += 1;
          break;
        case CACHE_READ:
          numCacheRead += 1;
          break;
      }
    }
  }

  public static void main(String[] args) {
    Node node = new Node();

    EvalTracker tracker = new EvalTracker();
    node.trace().setReceiver(tracker);

    node.lazyAttr();
    node.lazyAttr();
    node.nonLazyAttr();

    testEqual(1, tracker.numCacheWrite);
    testEqual(1, tracker.numCacheRead);
  }
}
