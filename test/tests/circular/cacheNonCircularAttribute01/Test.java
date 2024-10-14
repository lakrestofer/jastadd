// Tests that non-circular attributes that do no depend on circular attributes and
// that are declared as lazy are cached during evaluation of circular attributes,
// that is, the circular attribute depends on the non-circular attribute.
//
// .options=tracing=compute,circular | componentCheck tracing=compute,circular
// .source: 1.8
import static runtime.Test.*;

public class Test {
  static class EvalTracker implements ASTState.Trace.Receiver {
    public int d1;
    public int d2;

    public void accept(ASTState.Trace.Event event, ASTNode node, String attribute,
        Object params, Object value) {
      if (event == ASTState.Trace.Event.COMPUTE_BEGIN) {
        if (attribute.equals("Root.d1()")) {
          d1++;
        }
        if (attribute.equals("Root.d2()")) {
          d2++;
        }
      }
    }
  }

  public static void main(String[] args) {
    Root root = new Root();

    EvalTracker tracker = new EvalTracker();
    root.trace().setReceiver(tracker);

    testTrue(root.a1());
    testEqual(7, root.b());
    testEqual(2, root.d1());
    testEqual(1, root.d2());

    // Attributes d1() and d2() should only be computed once, since
    // they are cached and do not depend on circular attributes.
    testEqual(1, tracker.d1);
    testEqual(1, tracker.d2);
  }
}

