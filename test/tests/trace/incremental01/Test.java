// Tests tracing of computations.
// .options: tracing=flush incremental=param rewrite=cnta
// .source: 1.8
import static runtime.Test.*;

import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.*;

public class Test {
  static class EvalEventInformation {
    ASTState.Trace.Event event;
    ASTNode node;
    String attribute;
    Object params;
    Object value;
  }
  static class EvalTracker implements ASTState.Trace.Receiver {
    public ArrayList<EvalEventInformation> infos = new ArrayList<>();
    public void accept(ASTState.Trace.Event event, ASTNode node,
        String attribute, Object params, Object value) {
    } 
    public void accept(ASTState.Trace.Event event, String aspect, ASTNode node,
        String attribute, Object params, Object value) {
      switch (event) {
        case INC_FLUSH_START:
        case INC_FLUSH_END:
          EvalEventInformation info = new EvalEventInformation();
          info.event = event;
          info.node = node;
          info.attribute = attribute;
          info.params = params;
          info.value = value;
          infos.add(info);
          // System.err.println(event + ", " + node + ", " + attribute + ", " + params + ", " + value);
          break;
      }
    }
    public void reset() {
      infos.clear();
    }
  }

  public static void main(String[] args) {
    A a1 = new A(1);
    A a2 = new A(2);
    A a3 = new A(3);
    Root root = new Root(a1, new List().add(a2).add(a3));
     
    EvalTracker tracker = new EvalTracker();
    root.trace().setReceiver(tracker);

    // first time constructing B
    // calls expected are [setValue: 1, setChild: 2 (A in B, B in Root), setParent: 2 (B for A, Root for B)]
    // System.err.println("B b = root.getB()");
    B b = root.getB();
    testFor(tracker,
      startInfo(b.getA(), "setValue", "", 0),
      endInfo(b.getA(), "setValue", "", 0),
      startInfo(b, "ASTNode.setChild", 0, b.getA()),
      startInfo(b.getA(), "ASTNode.setParent", "", b),
      endInfo(b.getA(), "ASTNode.setParent", "", b),
      endInfo(b, "ASTNode.setChild", 0, b.getA()),
      startInfo(root, "ASTNode.setChild", 2, b),
      startInfo(b, "ASTNode.setParent", "", root),
      endInfo(b, "ASTNode.setParent", "", root),
      endInfo(root, "ASTNode.setChild", 2, b)
    );

    tracker.reset();
    // calls expected are [setValue: 1 (a4), setChild: 1 (a4 in root), setParent: 1 (root for a4)]
    // System.err.println("root.setMyA(a4)");
    A a4 = new A(4);
    root.setMyA(a4);
    testFor(tracker,
      startInfo(a4, "setValue", "", 4),
      endInfo(a4, "setValue", "", 4),
      startInfo(root, "ASTNode.setChild", 0, a4),
      startInfo(a4, "ASTNode.setParent", "", root),
      endInfo(a4, "ASTNode.setParent", "", root),
      endInfo(root, "ASTNode.setChild", 0, a4)
    );

    tracker.reset();
    // calls expected are [setValue: 2 (a5, a6), setChild: 2 (a5, a6 in root), setParent: 2 (root for a5, a6)]
    // System.err.println("root.setMyA(a6)");
    A a5 = new A(5);
    A a6 = new A(6);
    root.setMyA(a5);
    root.setMyA(a6);
    testFor(tracker,
      startInfo(a5, "setValue", "", 5),
      endInfo(a5, "setValue", "", 5),
      startInfo(a6, "setValue", "", 6),
      endInfo(a6, "setValue", "", 6),

      startInfo(root, "ASTNode.setChild", 0, a5),
      startInfo(a5, "ASTNode.setParent", "", root),
      endInfo(a5, "ASTNode.setParent", "", root),
      endInfo(root, "ASTNode.setChild", 0, a5),

      startInfo(root, "ASTNode.setChild", 0, a6),
      startInfo(a6, "ASTNode.setParent", "", root),
      endInfo(a6, "ASTNode.setParent", "", root),
      endInfo(root, "ASTNode.setChild", 0, a6)
    );

    tracker.reset();
    // calls expected are [setValue: 1 (a8), setChild: 1 (a8 in ManyList), setParent: 1 (ManyList for a8)]
    // System.err.println("root.addMany(a7)");
    A a7 = new A(7);
    root.addMany(a7);
    testFor(tracker,
      startInfo(a7, "setValue", "", 7),
      endInfo(a7, "setValue", "", 7),

      startInfo(root.getManyList(), "ASTNode.setChild", 2, a7),
      startInfo(a7, "ASTNode.setParent", "", root.getManyList()),
      endInfo(a7, "ASTNode.setParent", "", root.getManyList()),
      endInfo(root.getManyList(), "ASTNode.setChild", 2, a7)
    );

    tracker.reset();
    // calls expected are [setValue: 1 (a8), insertChild: 1 (a8 in ManyList), setParent: 1 (ManyList for a8)]
    // System.err.println("root.getManyList().insertChild(a8, 0)");
    A a8 = new A(8);
    root.getManyList().insertChild(a8, 0);
    testFor(tracker,
      startInfo(a8, "setValue", "", 8),
      endInfo(a8, "setValue", "", 8),

      startInfo(root.getManyList(), "ASTNode.insertChild", 0, a8),
      endInfo(root.getManyList(), "ASTNode.insertChild", 0, a8),
      startInfo(a8, "ASTNode.setParent", "", root.getManyList()),
      endInfo(a8, "ASTNode.setParent", "", root.getManyList())
    );

    tracker.reset();
    // calls expected are [removeChild: 1 (a8 in ManyList)]
    // setParent is not called directly when removing
    // System.err.println("root.getManyList().removeChild(1)");
    root.getManyList().removeChild(1);
    testFor(tracker,
      startInfo(root.getManyList(), "ASTNode.removeChild", 1, ""),
      endInfo(root.getManyList(), "ASTNode.removeChild", 1, "")
    );
  }

  private static EvalEventInformation startInfo(ASTNode node,
        String attribute, Object params, Object value) {
    return createInfo(ASTState.Trace.Event.INC_FLUSH_START, node, attribute, params, value);
  }

  private static EvalEventInformation endInfo(ASTNode node,
        String attribute, Object params, Object value) {
    return createInfo(ASTState.Trace.Event.INC_FLUSH_END, node, attribute, params, value);
  }

  private static EvalEventInformation createInfo(ASTState.Trace.Event event, ASTNode node,
        String attribute, Object params, Object value) {
    EvalEventInformation info = new EvalEventInformation();
    info.event = event;
    info.node = node;
    info.attribute = attribute;
    info.params = params;
    info.value = value;
    return info;
  }

  private static void testFor(EvalTracker tracker, EvalEventInformation... infos) {
    testEqual(tracker.infos.size(), infos.length);
    for (int i = 0; i < infos.length; i++) {
      EvalEventInformation actual = tracker.infos.get(i);
      EvalEventInformation expected = infos[i];
      testEqual(expected.event, actual.event);
      testEqual(expected.node, actual.node);
      testEqual(expected.attribute, actual.attribute);
      testEqual(expected.params, actual.params);
      testEqual(expected.value, actual.value);
    }
  }
}
