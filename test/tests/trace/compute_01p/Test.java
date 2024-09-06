// Tests tracing of computations.
// .options: tracing
// .source: 1.8
import static runtime.Test.*;

import java.io.PrintWriter;

public class Test {
  static class EvalTracker implements ASTState.Trace.Receiver {
    public int numComputeBegin;
    public int numComputeEnd;

    public void accept(ASTState.Trace.Event event, ASTNode node,
        String attribute, Object params, Object value) {
      switch (event) {
        case COMPUTE_BEGIN:
          numComputeBegin += 1;
          break;
        case COMPUTE_END:
          numComputeEnd += 1;
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
    node.nonLazyAttr();

    testEqual(tracker.numComputeBegin, tracker.numComputeEnd);
    testEqual(3, tracker.numComputeBegin);
  }
}
