JastAddTest
===========

This project tests the JastAdd2 code generator. In the future
it should be able to test JastAdd3 as well.

Quick Start
-----------

1. Rebuild `jastadd2.jar` in the root of this repository.
2. Run tests from the command line using either `ant` or `gradle`:
    * run all default tests (the TestShouldPass suite):
        - `$ ant`
        - `$ ./gradlew`
    * or, run some specific test(s):
        - `$ ant -Dtest=ast/flush01`
        - `$ ./gradlew -Dtest=ast/flush01`
    * or, run some tests matching a pattern:
        - `$ ant "-Dtest=ast/flush*"`
        - `$ ./gradlew "-Dtest=ast/flush*"`
    * or, run a specific test suite:
        - `$ ant -DtestSuite=tests.jastadd2.TestErrors`
        - `$ ./gradlew -DtestSuite=tests.jastadd2.TestErrors`
    * or, run with a specific jastadd jar file
        - `$ ant -Djastadd.jar=custom_build.jar`
        - `$ ./gradlew -Djastadd.jar=custom_build.jar`

    `-Dtest`, `-DtestSuite` and `-Djastadd.jar` can be combined with each other.
3. Run tests from Eclipse:
    * Select a test suite, e.g., `src/tests/jastadd2/TestShouldPass.java`
    * Select `Popup-menu->Run As->JUnit Test`
4. Add a new test case:
    * A test case is a directory with `.java .jrag .ast .description`, etc. files
    * Add a new test directory in the `tests` directory hierarchy.
    * To be recognized as a test case it *must* include either a `.java` or a `.description` file.
5. Add your own test suite:
    * Copy an existing test suite.
    * Change it to include and exclude parts of the full test suite.
6. Before pushing a new version of `JastAdd2` or `JastAddTest`, run the `TestShouldPass` suite.


Tests will timeout after 5 seconds. You can change the default timeout by specifying the
timeout in milliseconds using the `org.jastadd.testTimeout` system property.
When using Ant you can use the `-Dtimeout=X` option to change the timeout.

Test Organization
-----------------

Tests are organized into subdirectories according to which feature they are
intended to test. The test directory names end with a number to accomodate
multiple tests that test the same thing.

If a test tests several different features it should probably be divided into
several tests that test only one distinct feature.

Each test directory *must* contain either the file `Test.java` or
`Test.properties`. This is what identifies the directory as being a test
directory.

Each directory can have a `description` file, which is processed by
[Markdown](http://daringfireball.net/projects/markdown/syntax)
and included in the generated file `index.html`. Generate the index file
by the script `gendoc.sh`.

Test Suites
-----------

Test suites are located in `src/tests/jastadd2`. Each test suite is a regular
parameterized JUnit test. All test suites are identical except for their
properties which are setup in a static initializer.

This an example of how to initialize the test properties so that only tree copy
tests are run with JastAdd2, with the global tab indentation option:

    static {
      properties.put("jastadd3", "false");
      properties.put("jastadd.jar", "jastadd2.jar");
      properties.put("options", "indent=tab");
      properties.include("copy");
    }

Single tests or test suites can be included using `properties.include()`, and
tests can also be excluded by using `properties.exclude()`. The test suite
properties should not be confused with the individual test properties!  Test
suite properties are used to configure JastAdd2 for the entire test suite and
include/exclude individual tests. Test properties on the other hand configure a
single test.

Test Properties
---------------

Each test can have an optional `Test.properties` file that defines options for
that particular test. These options are available:

* `result` - defines the expected test result
* `classpath` - defines the classpath to be used when compiling and executing
  the test code
* `options` - defines sets of options to be added to the JastAdd configuration
  just for this test. See below for details.
* `sources` - a comma separated list of source files to be passed to JastAdd.
  This option is useful when the particular order of source files on the
JastAdd command line needs to be tested.

JastAdd flags can be added using the `options` test property. Flags are
separated by whitespace and are given without the '--' prefix. The test can
also be tested with different flags that are separated with the operator `|`,
for example,

    options = tracing=compute | tracing=compute componentCheck

Test Running
------------

The following operations are performed for each test:

1. JastAdd generates code for the test using all `.ast`, `.jrag` and `.jadd`
files in the test directory. Generated code is output in the `tmp` directory
and everything printed to `stderr` by JastAdd is dumped to the `jastadd.err`
file.
2. The generated Java code is compiled, along with all `.java` files in the
test directory.
3. The Test class is run with `stdout` redirected to the `out` file in the `tmp`
directory, and `stderr` redirected to the `err` file in the `tmp` directory.
4. If the `err` file is non-empty, the test case fails.  The `out` file is
compared to the `out.expected` file located in the test directory.

Each test can be configured to halt at any of the above steps by using the
`result` option in the `Test.properties` file:

* `JASTADD_FAIL` = JastAdd fails to generate code
* `JASTADD_PASS` = JastAdd generates code without error
* `JASTADD_ERR_OUTPUT` = JastAdd generates a specific error message (expected
  error message is found in the `jastadd.err.expected` file)
* `COMPILE_FAIL` = The generated code fails to compile
* `COMPILE_PASS` = The generated code compiles without error
* `EXEC_FAIL`    = The compiled code returns with non-zero exit status when
  executed and prints nothing on standard error
* `EXEC_PASS`    = The compiled code returns with zero exit status when
  executed and prints nothing on standard error
* `OUTPUT_PASS`  = All previous steps passed, and the output matches the
  expected output (expected output is found in the `output.expected` file)

By default the test will have the expected result `EXEC_PASS`.


Using the Runtime Framework
---------------------------

A runtime framework with methods like testTrue, similar to assertTrue in JUnit,
can be used to write tests. It can be used as follows:

    // Test.java
    import static runtime.Test.*;
    public class Test {
      public static void main(String args[]) {
        testTrue(true);
        testSame(new Object(), new Object());
      }
    }

The runtime framework is located in `framework/runtime`.
