// Tests the cache=config flag.
// .grammar: { Node; }
// .options: tracing cache=config
// .source: 1.8
import static runtime.Test.*;

import java.io.PrintWriter;

public class Test {
  static class CacheTracker implements ASTState.Trace.Receiver {
    public int numCacheWrite = 0;

    public void accept(ASTState.Trace.Event event, ASTNode node, String attribute,
        Object params, Object value) {
      if (event == ASTState.Trace.Event.CACHE_WRITE) {
        numCacheWrite += 1;
      }
    }
  }

  public static void main(String[] args) {
    Node node = new Node();

    CacheTracker tracker = new CacheTracker();
    node.trace().setReceiver(tracker);

    node.attr();

    testEqual(1, tracker.numCacheWrite);
  }
}
