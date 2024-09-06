// Tests tracing of computations.
// .options: tracing
// .source: 1.8
import static runtime.Test.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Test {
  static class EvalTracker implements ASTState.Trace.Receiver {
    public HashSet<String> aspectsVisited = new HashSet<String>();
    public void accept(ASTState.Trace.Event event, ASTNode node,
        String attribute, Object params, Object value) {
    } 
    public void accept(ASTState.Trace.Event event, String aspect, ASTNode node,
        String attribute, Object params, Object value) {
      switch (event) {
        case COMPUTE_BEGIN:
	  aspectsVisited.add(aspect);
          break;
      }
    }
  }

  public static void main(String[] args) {
   Decl decl = new Decl("a");
   Use use = new Use("a");
   Root root = new Root(new List().add(decl).add(use));
   
    EvalTracker tracker = new EvalTracker();
    root.trace().setReceiver(tracker);

    boolean ok = root.ok();

    testEquals(true, ok);
    testEqual(3, tracker.aspectsVisited.size());
  }
}
