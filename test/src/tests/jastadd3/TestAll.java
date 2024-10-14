package tests.jastadd3;

import core.TestConfiguration;
import core.TestRunner;
import core.ParallelParameterized;
import core.TestProperties;
import core.Util;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

/**
 * A parameterized Junit test to test JastAdd3
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
@RunWith(ParallelParameterized.class)
public class TestAll {

	private static final TestProperties properties = new TestProperties();
	static {
		properties.put("jastadd3", "true");
		properties.put("jastadd.jar", "jastadd-3.0.jar");
	}

	private final TestConfiguration test;

	/**
	 * Construct a new JastAdd test
	 * @param unitTest
	 */
	public TestAll(TestConfiguration unitTest) {
		this.test = unitTest;
	}

	/**
	 * Run the JastAdd test
	 */
	@Test
	public void runTest() throws IOException {
		TestRunner.runTest(test, properties);
	}

	@SuppressWarnings("javadoc")
	@Parameters(name = "{0}")
	public static Iterable<Object[]> getTests() {
		return Util.getTests(properties);
	}
}
