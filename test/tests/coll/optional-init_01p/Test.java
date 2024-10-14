// Collection declarations can omit the initialization
// expression if the attribute type is a concrete collection
// type that can be initialized by the default constructor.
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    Leaf[] l = new Leaf[10];
    for (int i = 0; i < 10; ++i) {
      l[i] = new Leaf();
    }
    Tree tree = new Tree(new Branch(new Branch(l[0], new Branch(l[1], l[2])),
        new Branch(new Branch(l[3], l[4]),
            new Branch(l[9], new Branch(new Branch(l[5], new Branch(l[6], l[7])), l[8])))));
    testThat(tree.leaves()).containsExactly((Leaf[]) l);
  }
}
