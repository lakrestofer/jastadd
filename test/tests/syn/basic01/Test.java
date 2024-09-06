import static runtime.Test.*;

public class Test {
    public static final void main(String[] args) {
        Node node = new Node();

        testEqual(13, node.basicAttr());
    }
}
