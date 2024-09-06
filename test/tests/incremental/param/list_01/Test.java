// Regression test for runtime error caused by mismatch in child handler array
// size and child array size.
// This test caused an array index out of bounds exception in insertChild.
// https://bitbucket.org/jastadd/jastadd2/issues/300
// .options: incremental=param rewrite=cnta
// .grammar: { A ::= B*; B; }
public class Test {
  public static void main(String[] args) {
    A a = new A();
    a.getBList().insertChild(new B(), 0);
    a.getBList().insertChild(new B(), 0);
    a.getBList().insertChild(new B(), 0);
  }
}
