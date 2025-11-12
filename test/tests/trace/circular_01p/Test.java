// Regression test checking that CIRCULAR_CASE2_START and
// CIRCULAR_CASE2_RETURN events happen equal number of times.
// More specifically: there used to be a missing RETURN event.
// .options: tracing=circular
// .grammar: { Node; }
// .source: 1.8
import static runtime.Test.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

public class Test {

  static class EvalTracker implements ASTState.Trace.Receiver {
    public List<String> events = new ArrayList<>();

    public void accept(ASTState.Trace.Event event, ASTNode node,
        String attribute, Object params, Object value) {
      events.add(String.format("%s %s", event, attribute));
    }
  }

  static class StateMachine {
    boolean isInsideComputeA = false;
    boolean isInsideComputeB = false;
    boolean didComputeB = false;

    void enterA() {
        testEqual(false, isInsideComputeA);
        testEqual(false, isInsideComputeB);
        isInsideComputeA = true;
    }
    void exitA() {
        testEqual(true, isInsideComputeA);
        testEqual("Must exit B before exiting A", false, isInsideComputeB);
        isInsideComputeA = false;
    }
    void enterB() {
        testEqual(true, isInsideComputeA);
        testEqual(false, isInsideComputeB);
        isInsideComputeB = true;
        didComputeB = true;
    }
    void exitB() {
        testEqual(true, isInsideComputeA);
        testEqual(true, isInsideComputeB);
        isInsideComputeB = false;
    }
  }

  private static List<String> setup(Function<Node, Integer> performEvaluation) {
      Node node = new Node();
      EvalTracker tracker = new EvalTracker();
      node.trace().setReceiver(tracker);
      testEqual(1, performEvaluation.apply(node));
      return tracker.events;
  }

  private static void testUnparameterized() {
    StateMachine sm = new StateMachine();
    List<String> events = setup(node -> node.a());
    for (String s : events) {
      switch (s) {
        case "CIRCULAR_CASE1_START Node.a()": sm.enterA(); break;
        case "CIRCULAR_CASE1_RETURN Node.a()": sm.exitA(); break;
        case "CIRCULAR_CASE2_START Node.b()": sm.enterB(); break;
        case "CIRCULAR_CASE2_RETURN Node.b()": sm.exitB(); break;
      }
    }
    testEqual(false, sm.isInsideComputeA);
    testEqual("ev: " + events , false, sm.isInsideComputeB);
    testEqual(true, sm.didComputeB);
  }

  private static void testParameterized() {
    StateMachine sm = new StateMachine();
    for (String s : setup(node -> node.pa(1))) {
      switch (s) {
        case "CIRCULAR_CASE1_START Node.pa(int)": sm.enterA(); break;
        case "CIRCULAR_CASE1_RETURN Node.pa(int)": sm.exitA(); break;
        case "CIRCULAR_CASE2_START Node.pb(int)": sm.enterB(); break;
        case "CIRCULAR_CASE2_RETURN Node.pb(int)": sm.exitB(); break;
      }
    }
    testEqual(false, sm.isInsideComputeA);
    testEqual(false, sm.isInsideComputeB);
    testEqual(true, sm.didComputeB);
  }

  public static void main(String[] args) {
    testUnparameterized();
    testParameterized();
  }
}
