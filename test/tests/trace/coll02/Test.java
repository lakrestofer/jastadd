// Tests the flag --tracing=coll.
// .options: tracing=coll rewrite
// .source: 1.8
import static runtime.Test.*;

import java.util.Set;

public class Test {

  static class EvalTracker implements ASTState.Trace.Receiver {
    public int contributions;

    public void accept(ASTState.Trace.Event event, ASTNode node,
          String attribute, Object params, Object value) {
      switch (event) {
        case CONTRIBUTION_CHECK_BEGIN:
	  break;
        case CONTRIBUTION_CHECK_END:
          break;
	case CONTRIBUTION_CHECK_MATCH:
	  contributions++;
	  break;
        default:
          fail("Other trace event than coll: " + event);
      }
    }
  }

  public static void main(String[] args) {
    Node node = new Node(new B(), new C());

    EvalTracker tracker = new EvalTracker();
    node.trace().setReceiver(tracker);

    Set<String> greetings = node.greetings();
    testEquals(tracker.contributions, greetings.size());
  }
}
