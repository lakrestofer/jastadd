// Test incremental recompute granularity for inherited lookup attribute.
// .options: rewrite=cnta incremental=param
import static runtime.Test.*;

public class Test {

  public static void main(String[] args) {
    Block b = new Block();
    b.addStmt(new Decl("a1"));
    b.addStmt(new Decl("b"));
    b.addStmt(new Decl("a2"));
    b.addStmt(new Decl("b"));
    b.addStmt(new Decl("a3"));
    b.addStmt(new Use("b"));
    b.addStmt(new Decl("b"));

    Decl
        a1 = (Decl) b.getStmt(0),
        b1 = (Decl) b.getStmt(1),
        a2 = (Decl) b.getStmt(2),
        b2 = (Decl) b.getStmt(3),
        a3 = (Decl) b.getStmt(4);
    Use u = (Use) b.getStmt(5);

    // Check memoization:
    testSame(u.decl(), u.decl());

    // Check name binding:
    testSame(b2, u.decl().x);

    Value<Decl> v1 = u.decl();

    // Transform: remove last declaration (does not affect the use).
    b.getStmtList().removeChild(6);

    // Check that decl() was not recomputed.
    testSame(v1, u.decl());

    // Transform: remove a2 decl, does not affect the use but it is recomputed.
    b.getStmtList().removeChild(2);

    // Check that decl() was recomputed.
    testNotSame(v1, u.decl());
    testSame(b2, u.decl().x);

    // Transform: remove a3, decl is recomputed but not changed.
    b.getStmtList().removeChild(3);

    // Check that decl() was recomputed.
    testNotSame(v1, u.decl());
    testSame(b2, u.decl().x);

    // Transform: remove second b decl, the use is affected.
    b.getStmtList().removeChild(2);

    // Check that decl() was recomputed.
    testSame(b1, u.decl().x);
  }
}
