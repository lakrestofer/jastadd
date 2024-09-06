// Liveness analysis demo.
// .grammar: { Vertex ::= <Index:Integer>; }
// .options: safeLazy | safeLazy concurrent

import static runtime.Test.*;

import java.util.BitSet;

public class Test {
  public static void main(String[] args) {
    Vertex[] vertex = new Vertex[9];
    for (int i = 0; i < vertex.length; ++i) {
      vertex[i] = new Vertex(i);
    }

    int x = 0, y = 1, u = 2, w = 3;

    vertex[0].def.set(x);
    vertex[0].def.set(u);
    vertex[0].def.set(w);
    connect(vertex[0], vertex[1]);
    connect(vertex[0], vertex[7]);

    vertex[1].use.set(y);
    connect(vertex[1], vertex[2]);
    connect(vertex[1], vertex[6]);

    vertex[2].def.set(y);
    vertex[2].use.set(y);
    connect(vertex[2], vertex[3]);
    connect(vertex[2], vertex[4]);

    vertex[3].def.set(u);
    vertex[3].use.set(x);
    connect(vertex[3], vertex[5]);

    vertex[4].def.set(w);
    vertex[4].use.set(x);
    connect(vertex[4], vertex[5]);

    connect(vertex[5], vertex[6]);

    vertex[6].def.set(x);
    vertex[6].use.set(x);
    vertex[6].use.set(u);
    vertex[6].use.set(w);
    connect(vertex[6], vertex[7]);
    connect(vertex[6], vertex[1]);

    vertex[7].def.set(x);
    vertex[7].use.set(u);
    connect(vertex[7], vertex[8]);

    vertex[8].use.set(x);

    String _x = String.format("{%d}", x);
    String _y = String.format("{%d}", y);
    String _u = String.format("{%d}", u);
    String _xyu = String.format("{%d, %d, %d}", x, y, u);
    String _xyw = String.format("{%d, %d, %d}", x, y, w);
    String _xyuw = String.format("{%d, %d, %d, %d}", x, y, u, w);

    testEqual(_y, vertex[0].in().toString());
    testEqual(_xyuw, vertex[0].out().toString());

    testEqual(_xyuw, vertex[1].in().toString());
    testEqual(_xyuw, vertex[1].out().toString());

    testEqual(_xyuw, vertex[2].in().toString());
    testEqual(_xyuw, vertex[2].out().toString());

    testEqual(_xyw, vertex[3].in().toString());
    testEqual(_xyuw, vertex[3].out().toString());

    testEqual(_xyu, vertex[4].in().toString());
    testEqual(_xyuw, vertex[4].out().toString());

    testEqual(_xyuw, vertex[5].in().toString());
    testEqual(_xyuw, vertex[5].out().toString());

    testEqual(_xyuw, vertex[6].in().toString());
    testEqual(_xyuw, vertex[6].out().toString());

    testEqual(_u, vertex[7].in().toString());
    testEqual(_x, vertex[7].out().toString());
  }

  static void connect(Vertex a, Vertex b) {
    a.succ.add(b);
    b.pred.add(a);
  }
}
