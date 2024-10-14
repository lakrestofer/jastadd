// Tests a declaration order bug reported by Alfred Theorin. Test case provided by Alfred.
// https://bitbucket.org/jastadd/jastadd2/issue/194/declaration-order-dependency-for
// .result=COMPILE_PASS
// .tags=bug,syn,interface
public class Test implements MyInterface {
	@Override
	public int myValue1() {
		return 0;
	}

	@Override
	public int myValue2() {
		return 1;
	}
}

