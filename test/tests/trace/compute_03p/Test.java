// Regression test checking that 'value' in COMPUTE_END
// is not an empty string for inlinable blocks.
// .options: tracing=compute
// .grammar: { Node; }
// .source: 1.8
import static runtime.Test.*;

import java.io.PrintWriter;

public class Test {
  static class EvalTracker implements ASTState.Trace.Receiver {
    public int numComputeEnd;
    public Object lastComputeEndValue;

    public void accept(ASTState.Trace.Event event, ASTNode node,
        String attribute, Object params, Object value) {
      switch (event) {
        case COMPUTE_END:
          numComputeEnd += 1;
          lastComputeEndValue = value;
          break;
      }
    }
  }

  public static void main(String[] args) {
    Node node = new Node();

    EvalTracker tracker = new EvalTracker();
    node.trace().setReceiver(tracker);

    testEqual("Hello", node.echo("Hello"));
    testEqual("Hello", tracker.lastComputeEndValue);
    testEqual(1, tracker.numComputeEnd);
  }
}
