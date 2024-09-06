// Similar to cacheNonCircularAttribute 01, but with more attributes.
//
// .options = tracing=compute,circular | componentCheck tracing=compute,circular
// .source: 1.8
import static runtime.Test.*;

public class Test {
  static class EvalTracker implements ASTState.Trace.Receiver {
    public int d1;
    public int d2;
    public int e1;
    public int e2;
    public int f1;
    public int f2;

    public void accept(ASTState.Trace.Event event, ASTNode node, String attribute,
        Object params, Object value) {
      if (event == ASTState.Trace.Event.COMPUTE_BEGIN) {
        if (attribute.equals("Root.d1()")) {
          d1++;
        } else if (attribute.equals("Root.d2()")) {
          d2++;
        } else if (attribute.equals("Root.e1()")) {
          e1++;
        } else if (attribute.equals("Root.e2()")) {
          e2++;
        } else if (attribute.equals("Root.f1()")) {
          f1++;
        } else if (attribute.equals("Root.f2()")) {
          f2++;
        }
      }
    }
  }

  public static void main(String[] args) {
    Root root = new Root();

    EvalTracker tracker = new EvalTracker();
    root.trace().setReceiver(tracker);

    testTrue(root.a1());
    testEqual(9, root.b1());
    testEqual(7, root.b2());
    testEqual(2, root.d1());
    testEqual(1, root.d2());
    testEqual(2, root.e1());
    testEqual(1, root.e2());
    testEqual(2, root.f1());
    testEqual(1, root.f2());

    // Attributes d1(), d2(), e1(), e2(), f1(), f2() should only be computed
    // once, since they are cached and do not depend on circular attributes.
    testEqual(1, tracker.d1);
    testEqual(1, tracker.d2);
    testEqual(1, tracker.e1);
    testEqual(1, tracker.e2);
    testEqual(1, tracker.f1);
    testEqual(1, tracker.f2);
  }
}

