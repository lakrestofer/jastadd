// Tests nested rewrites.
// This test does not work with the old rewrite algorithm because of ciruclar
// attribute dependencies.
// .options: visitCheck=false rewrite=cnta
import static runtime.Test.testEqual;

public class Test {
  public static void main(String[] args) {
    Prog p = new Prog(
        new List().add(
          new CompUnit(
            "comp",
            new List().add(
              new ClassDecl("Object", new TypeName("Object"),
                new List().add(
                  // PackageName "comp" . TypeName "Object"
                  new FieldDecl(new Dot("dot", new ParseName("comp"), new ParseName("Object")), "f",
                    // PackageName "comp" . TypeName "Object" . ExpressionName "f"
                    new Dot("dot", new ParseName("comp"), new Dot("dot", new ParseName("Object"), new ParseName("f")))
                    //new ParseName("Object")
                    )
                  )
                )
              )
            )
          )
        );
    p.traverse();
    p.getCompUnit(0).getClassDecl(0).getBodyDecl(0);
    testEqual(PackageName.class, ((Dot)((FieldDecl)p.getCompUnit(0).getClassDecl(0).getBodyDecl(0)).getFieldType()).getLeft().getClass());
    testEqual(TypeName.class, ((Dot)((FieldDecl)p.getCompUnit(0).getClassDecl(0).getBodyDecl(0)).getFieldType()).getRight().getClass());
    testEqual(PackageName.class, ((Dot)((FieldDecl)p.getCompUnit(0).getClassDecl(0).getBodyDecl(0)).getExpr()).getLeft().getClass());
    testEqual(TypeName.class, ((Dot)((Dot)((FieldDecl)p.getCompUnit(0).getClassDecl(0).getBodyDecl(0)).getExpr()).getRight()).getLeft().getClass());
    testEqual(ExpressionName.class, ((Dot)((Dot)((FieldDecl)p.getCompUnit(0).getClassDecl(0).getBodyDecl(0)).getExpr()).getRight()).getRight().getClass());
  }
}
