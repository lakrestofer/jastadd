// Tests the flush=attr flag using the tracing=flush flag.
// .options: tracing=flush
// .source: 1.8
import static runtime.Test.*;

import java.io.PrintWriter;
import java.util.Map;

public class Test {
  static class FlushTracker implements ASTState.Trace.Receiver {
    public int numFlushed = 0;

    public void accept(ASTState.Trace.Event event, ASTNode node, String attribute,
        Object params, Object value) {
      if (event == ASTState.Trace.Event.FLUSH_ATTR) {
        numFlushed += 1;
      }
    }
  }

  public static void main(String[] args) {
    Node node = new Node();

    FlushTracker tracker = new FlushTracker();
    node.trace().setReceiver(tracker);

    node.attr();

    node.flushCache();

    testEqual(1, tracker.numFlushed);
  }
}
