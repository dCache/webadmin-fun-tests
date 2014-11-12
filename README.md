dCache webadmin functional tests
================================

This repository contains functional tests for dCache's webadmin
interface.

When run, the test-suite will go through a series of exercises that
should check the behaviour of the webadmin interface.  For some
drivers, each test will open a web-browser window, which is closed
automatically when that test completes.

The result of the tests is written to the console and (optionally) to
an XML.

Building
--------

The command `mvn compile assembly:single` will generate a stand-alone
jar file, one that contains all the dependencies.  The result is the
file `webadmin-testsuite-allinone-<version>.jar` located in the
`target/` directory.

Running
-------

At its simplest, running the functional tests is `java -jar
webadmin-testsuite-allinone-<version>.jar`.  This will run the
functional test-suite against a default-configured system-test on the
local machine using htmlunit browser.

Alternative or additional behaviour may be configured using system
properties (i.e., adding `-D<name>=<value>`)

*   `webadmin.url` This is the URL of webadmin.  If not specified
    `http://localhost:2288/` is used, which is good for the
    system-test.

*   `jenkins.path` In addition to the test results written to the
    console, this test-suite can also write output in an XML format
    compatible with Jenkin's JUnit parser.  This property controls the
    location of this XML file.  If not specified then no such output
    is generated.

*   `screenshot.path` The path of a directory into which screenshots
    are written.  This is done when a test fails, to allow further
    investigation.  The screenshots files have names like
    `screenshot-<class-name>-<method-name>.png`, where `<class-name>`
    is the simple name for the unit test and `<method-name>` is the
    name of the test that failed.

    Taking screenshots is not supported by all drivers; for example,
    the default driver (`htmlunit`) does not support taking
    screenshots.  The test-suite will display an error message and halt
    if screenshots are requested for a driver that has no such
    support.

    If no `screenshot.path` property is specified then no
    screenshots are generated.

*   `driver` This property controls which web-browser to use for the
    tests.  Valid values are `firefox`, `safari`, `ie`, `chrome`,
    `phantomjs` and `htmlunit`.  If this property isn't specified then
    `htmlunit` is used.

    The `firefox`, `safari`, `ie` and `chrome` options result in the
    this test-suite driving the equivalent web-browser.

    The `phantomjs` option results in
    [PhantomJS](http://phantomjs.org/) being used.  This is a headless
    web-browser, based on WebKit, that is ideal for such functional
    testing.

    The `htmlunit` option means a built-in web-browser is used.  This
    provides limited capabilities compared to the other options, but
    has virtue in not requiring any external dependencies.

    All drivers except `htmlunit` support taking screenshots, and all
    drivers except `htmlunit` require the appropriate web-browser be
    installed on the test system.

*   `tests` This property is a comma-separated list of tests to run.
    Each item is either a simple class name or a class' canonical
    name.  Simple class names are taken from the
    "org.dcache.webtests.webadmin.tests" package.

The java process exits with a return-code to indicate what happened:

*   `0` all tests were either successful or ignored. 
*   `1` one or more tests failed or were in error.
*   `2` bad options were supplied.
