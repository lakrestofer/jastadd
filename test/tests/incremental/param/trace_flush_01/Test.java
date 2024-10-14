// Test that the flushing API is working together with incremental evaluation
// .options: tracing=flush cache=all rewrite=cnta incremental=param
// .source: 1.8
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    test(new B());
    ASTNode.resetState(); // Remove trace receiver so that the setToken(1) events are not processed with the old receiver.

    test(new A());
  }

  private static void test(final A subjectUnderTest) {
    subjectUnderTest.setToken(1);
    final int[] counter = {0};
    System.out.println("subject: " + subjectUnderTest);
    System.out.println("trace: " + subjectUnderTest.trace());
    subjectUnderTest.trace().setReceiver(new ASTState.Trace.Receiver() {
      @Override
      public void accept(ASTState.Trace.Event event, ASTNode node, String attribute, Object params, Object value) {
        if (event == ASTState.Trace.Event.INC_FLUSH_ATTR) {
          testSame(subjectUnderTest, node);
          testEquals("value", attribute);
          counter[0]++;
        } else {
          testTrue(event == ASTState.Trace.Event.INC_FLUSH_START
              || event == ASTState.Trace.Event.INC_FLUSH_END);
        }
      }
    });
    // counter is still zero, as there was no flush yet
    int actualValue = subjectUnderTest.value();
    testEquals(1, actualValue);
    testEquals(0, counter[0]);

    // no change to token, so value is read from cache, and no flush
    actualValue = subjectUnderTest.value();
    testEquals(1, actualValue);
    testEquals(0, counter[0]);

    // after change to token, value needs to be recomputed
    subjectUnderTest.setToken(5);
    actualValue = subjectUnderTest.value();
    testEquals(5, actualValue);
    testEquals(1, counter[0]);
  }
}
