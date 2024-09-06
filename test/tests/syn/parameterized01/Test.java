import static runtime.Test.*;

public class Test {
    public static final void main(String[] args) {
        Node node = new Node();

        // 0+13 = 13
        testEqual(13, node.paramAttr(0));

        // 13+13 = 26
        testEqual(26, node.paramAttr(13));
    }
}
