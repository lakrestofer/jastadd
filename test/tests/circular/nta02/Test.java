// Tests parameterized circular NTA evaluation and cache flushing.
//
// .options: tracing=circular,flush
// .source: 1.8
import static runtime.Test.*;

public class Test {
  static class EvalTracker implements ASTState.Trace.Receiver {
    public int numFlushed;
    public int numCase1Start;
    public int numCase1Change;
    public int numCase3Return;

    public void accept(ASTState.Trace.Event event, ASTNode node, String attribute,
        Object params, Object value) {
      switch (event) {
        case FLUSH_ATTR:
          numFlushed += 1;
          break;
        case CIRCULAR_NTA_CASE1_START:
          numCase1Start += 1;
          break;
        case CIRCULAR_NTA_CASE1_CHANGE:
          numCase1Change += 1;
          break;
        case CIRCULAR_NTA_CASE3_RETURN:
          numCase3Return += 1;
          break;
      }
    }
  }

  public static void main(String[] args) {
    Root root = new Root();

    EvalTracker tracker = new EvalTracker();
    root.trace().setReceiver(tracker);

    A a = root.a(0);

    testTrue(a.isC());

    root.flushCache();

    testEqual(1, tracker.numFlushed);
    testEqual(1, tracker.numCase1Start);
    testEqual(2, tracker.numCase1Change);
    testEqual(3, tracker.numCase3Return);
  }
}
