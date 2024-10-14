// .result=COMPILE_PASS
public class Test implements InterfaceA, InterfaceB {
	@Override
	public InterfaceA foo() {
		return this;
	}

	@Override
	public void bar(InterfaceB obj) {
	}
}
