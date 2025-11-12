// Regression test checking that "final cycle" tracing
// events include COMPUTE_BEGIN for the driver attribute.
// .options: tracing=compute
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
        testEqual(false, isInsideComputeB);
        isInsideComputeA = false;
    }
    void enterB() {
        testEqual("b should only run while a is running", true, isInsideComputeA);
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
      testEqual(0, performEvaluation.apply(node));
      return tracker.events;
  }

  private static void testUnparameterized() {
    StateMachine sm = new StateMachine();
    for (String s : setup(node -> node.a())) {
      switch (s) {
        case "COMPUTE_BEGIN Node.a()": sm.enterA(); break;
        case "COMPUTE_END Node.a()": sm.exitA(); break;
        case "COMPUTE_BEGIN Node.b()": sm.enterB(); break;
        case "COMPUTE_END Node.b()": sm.exitB(); break;
      }
    }
    testEqual(true, sm.didComputeB);
  }

  private static void testParameterized() {
    StateMachine sm = new StateMachine();
    for (String s : setup(node -> node.pa(0))) {
      switch (s) {
        case "COMPUTE_BEGIN Node.pa(int)": sm.enterA(); break;
        case "COMPUTE_END Node.pa(int)": sm.exitA(); break;
        case "COMPUTE_BEGIN Node.pb(int)": sm.enterB(); break;
        case "COMPUTE_END Node.pb(int)": sm.exitB(); break;
      }
    }
    testEqual(true, sm.didComputeB);
  }

  public static void main(String[] args) {
    testUnparameterized();
    testParameterized();
  }
}
