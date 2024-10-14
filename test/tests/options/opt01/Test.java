// .result: COMPILE_PASS
// .options: Opt=XOpt
public class Test {
	C c = new C(new XOpt());
	XOpt x = c.getBOpt();
}
