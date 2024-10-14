// Test to check that cached values of primitive-typed attributes are stored correctly.
import static runtime.Test.*;

public class Test {
 public static void main(String[] args) {
   A a = new A();
   testEqual("0",    "" + a.byteValue());
   testEqual("0",    "" + a.byteValue());
   testEqual("1",    "" + a.shortValue());
   testEqual("1",    "" + a.shortValue());
   testEqual("2",    "" + a.intValue());
   testEqual("2",    "" + a.intValue());
   testEqual("a",    "" + a.charValue());
   testEqual("a",    "" + a.charValue());
   testEqual("3",    "" + a.longValue());
   testEqual("3",    "" + a.longValue());
   testEqual("0.5",  "" + a.floatValue());
   testEqual("0.5",  "" + a.floatValue());
   testEqual("0.6",  "" + a.doubleValue());
   testEqual("0.6",  "" + a.doubleValue());
   testEqual("true", "" + a.booleanValue());
   testEqual("true", "" + a.booleanValue());
   testEqual("str",  "" + a.stringValue());
   testEqual("str",  "" + a.stringValue());
 }

}
