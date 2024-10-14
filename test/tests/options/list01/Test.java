// .result: COMPILE_PASS
// .options: List=XList
public class Test {
	C c = new C(new XList());
	XList x = c.getBList();
}
