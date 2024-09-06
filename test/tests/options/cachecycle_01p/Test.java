// Test that the cacheCycle option memoizes attributes by performing an
// extra compute cycle and flagging all attributes used in the last cycle
// as computed.
// https://bitbucket.org/jastadd/jastadd2/issues/295/cachecycle-option-is-not-working
// .grammar: { A; }
// .options: cacheCycle tracing=compute
// .source: 1.8
import java.util.*;

import static runtime.Test.*;

public class Test {

  static class ComputeLog implements ASTState.Trace.Receiver {
    public Collection<String> log = new ArrayList<>();
    @Override
    public void accept(ASTState.Trace.Event event, ASTNode node, String attribute,
        Object params, Object value) {
      if (event == ASTState.Trace.Event.COMPUTE_BEGIN) {
        log.add(attribute);
      }
    }
  }

  public static void main(String[] args) {
    A a = new A();
    a.x();

    // Set up compute logging in order to check which attributes were computed.
    ComputeLog log = new ComputeLog();
    a.trace().setReceiver(log);

    // Note: x() uses y(), so y() should already be memoized.
    a.y();
    testThat(log.log).isEmpty();
  }
}
