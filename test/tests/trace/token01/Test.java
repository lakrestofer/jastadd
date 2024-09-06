// Tests the flag --tracing=token.
// .options: tracing=token rewrite
// .source: 1.8
import static runtime.Test.*;

public class Test {

  static class EvalTracker implements ASTState.Trace.Receiver {
    public int reads;

    public void accept(ASTState.Trace.Event event, ASTNode node,
          String attribute, Object params, Object value) {
      switch (event) {
        case TOKEN_READ:
	  reads++;
	  break;
        default:
          fail("Other trace event than token: " + event);
      }
    }
  }

  public static void main(String[] args) {
    Node node = new Node(new B(4, 5), new C(5, 6), "Var");

    EvalTracker tracker = new EvalTracker();
    node.trace().setReceiver(tracker);

    String desc = node.desc();
    testEquals(tracker.reads, 5);
  }
}
