import static runtime.Test.*;

public class Test {
	public static void main(String[] args) {
		Node node = new Node(true,
				(byte) 255,
				'X',
				(short) 0xBFF,
				0xBEEEF,
				-1L,
				3.1415f,
				3.141592);

		test(node.getBooleanToken() == true);
		test(node.getByteToken() == (byte) 255);
		test(node.getCharToken() == 'X');
		test(node.getShortToken() == 0xBFF);
		test(node.getIntToken() == 0xBEEEF);
		test(node.getLongToken() == -1L);
		test(node.getFloatToken() == 3.1415f);
		test(node.getDoubleToken() == 3.141592);
	}
}
