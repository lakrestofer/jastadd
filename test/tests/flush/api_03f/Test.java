// Test that the flushing API is not generated with --flush=none.
// .result: COMPILE_FAIL
// .options: flush=none
// .grammar: { A; }
public class Test {
  public static void main(String[] args) {
    new ASTNode().flushCache(); // Compie fails: flush methods not generated.
  }
}
