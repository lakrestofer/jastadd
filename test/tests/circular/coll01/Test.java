// This tests the `altReachable` attribute from the StateMachine example. The
// attribute is implemented as a ciruclar collection attribute.
//
// See http://jastadd.org/web/examples.php?example=StateMachine for more info.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    StateMachine sm = new StateMachine();
    int N = 10;
    State[] s = new State[N];
    for (int i = 0; i < N; ++i) {
      s[i] = new State("S"+i);
      sm.addDeclaration(s[i]);
    }
    transition(sm, 0, 1);
    transition(sm, 1, 2);
    transition(sm, 2, 3);
    transition(sm, 3, 4);
    transition(sm, 4, 5);
    transition(sm, 5, 6);
    transition(sm, 6, 7);
    transition(sm, 7, 8);
    transition(sm, 8, 9);
    testEqual(9, s[0].altReachable().size());
    testTrue(s[0].altReachable().contains(s[1]));
    testTrue(s[0].altReachable().contains(s[2]));
    testTrue(s[0].altReachable().contains(s[9]));
  }

  static void transition(StateMachine sm, int s, int t) {
    sm.addDeclaration(new Transition("T_"+s+"_"+t, "S"+s, "S"+t));
  }
}
