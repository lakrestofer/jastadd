// Tests evaluation of rewrite using circular NTA algorithm.
// .options=rewrite=cnta tracing=circular,flush
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
    Program p = new Program(new B());

    EvalTracker tracker = new EvalTracker();
    p.trace().setReceiver(tracker);

    testEqual(-1, p.getA().value());
    p.flushCache(); // Rewrite is cached on the B node, not Program. This has no effect.

    testEqual(0, tracker.numFlushed);
    testEqual(1, tracker.numCase1Start);
    testEqual(1, tracker.numCase1Change);
    testEqual(0, tracker.numCase3Return);
  }
}
