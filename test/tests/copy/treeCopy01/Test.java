// Copying a node with treeCopy should trigger rewrites and copy the rewritten result.
// .options=rewrite
import static runtime.Test.*;

public class Test {
  public static void main(String[] args) {

    A childNoTransform = new B();
    Program p = new Program(childNoTransform);

    Program pCopy = p.treeCopyNoTransform();

    // treeCopyNoTransform should not evaluate rewrite while copying, child should still be a B
    testEqual(childNoTransform.getClass().getSimpleName(),
        pCopy.getANoTransform().getClass().getSimpleName());

    Program pCopyEval = p.treeCopy();

    // treeCopy should trigger rewrites during copying, child should now be a C and not a B
    testNotSame(childNoTransform.getClass().getSimpleName(),
        pCopyEval.getANoTransform().getClass().getSimpleName());
  }
}
