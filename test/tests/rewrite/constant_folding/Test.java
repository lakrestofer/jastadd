// This test checks that optimal rewrite ordering happens for
// top-down single-step rewrites.
// .options: rewrite=cnta | rewrite=true
// .result: OUTPUT_PASS
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {
    ExprStmt exp = new ExprStmt(
        new Add(
          new Add(new Const(12), new Const(4)),
          new Const(5)
        )
    );
    // Note: constValue() throws an error if exp.getExpr() is not transformed.
    testEqual(21, exp.getExpr().constValue());

    exp = new ExprStmt(
        new Add(
          new Add(new Const(12), new Const(4)),
          new Add(
            new Add(new Const(-2), new Const(1)),
            new Const(-3)
          ))
    );
    testEqual(12, exp.getExpr().constValue());

    // Non-constant:
    exp = new ExprStmt(
        new Add(
          new Var("x"),
          new Add(
            new Add(new Const(-2), new Const(1)),
            new Const(-3)
          ))
    );
    testFalse(exp.getExpr().isConstant());
  }
}
