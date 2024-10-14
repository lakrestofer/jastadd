import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		System.out.println("Starting...");
		test(!new UnNamed().hasB());
		test(new UnNamed(new List(new B())).hasB());
		test(!new Named().hasName());
		test(new Named(new List(new B())).hasName());
	}
}
