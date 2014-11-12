package org.dcache.webtests.webadmin;

import com.google.common.base.Splitter;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;

import org.dcache.webtests.drivers.WebDriverFactory;
import org.dcache.webtests.webadmin.tests.ActiveTransfersTests;
import org.dcache.webtests.webadmin.tests.AlarmsTests;
import org.dcache.webtests.webadmin.tests.BillingPlotsTests;
import org.dcache.webtests.webadmin.tests.CellAdminTests;
import org.dcache.webtests.webadmin.tests.CellServicesTests;
import org.dcache.webtests.webadmin.tests.InfoXmlTests;
import org.dcache.webtests.webadmin.tests.LoginTests;
import org.dcache.webtests.webadmin.tests.OverviewTests;
import org.dcache.webtests.webadmin.tests.PoolAdminTests;
import org.dcache.webtests.webadmin.tests.PoolQueuePlotsTests;
import org.dcache.webtests.webadmin.tests.PoolQueuesTests;
import org.dcache.webtests.webadmin.tests.PoolSelectionSetupTests;
import org.dcache.webtests.webadmin.tests.PoolUsageTests;
import org.dcache.webtests.webadmin.tests.PoolgroupsTests;
import org.dcache.webtests.webadmin.tests.SpaceTokensTests;
import org.dcache.webtests.webadmin.tests.TapeTransferQueueTests;

/**
 *  Start JUnit for the web-admin tests.
 */
public class Runner
{
    public static final String TARGET_URL = System.getProperty("webadmin.url", "http://localhost:2288/");
    public static final WebDriverFactory WEB_DRIVER_FACTORY = createWebDriverFactory();
    public static final File SNAPSHOT_PATH = getSnapshotPath();

    public static final String TEST_PACKAGE = "org.dcache.webtests.webadmin.tests";

    private static final Class<?>[] DEFAULT_CLASSES_TO_TEST = {
        OverviewTests.class, CellServicesTests.class, PoolUsageTests.class,
        PoolQueuesTests.class, PoolQueuePlotsTests.class, PoolgroupsTests.class,
        TapeTransferQueueTests.class, ActiveTransfersTests.class,
        BillingPlotsTests.class, PoolSelectionSetupTests.class,
        PoolAdminTests.class, CellAdminTests.class, InfoXmlTests.class,
        SpaceTokensTests.class, AlarmsTests.class, LoginTests.class};


    private static boolean isSnapshotSupported;

    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException
    {
        JUnitCore core = new JUnitCore();
        core.addListener(new PrintStreamReporter(System.out));

        String path = System.getProperty("jenkins.path");
        if (path != null) {
            System.out.println("Writing Jenkins XML report to " + path);
            core.addListener(new JenkinsResultReporter(path));
        }

        Result result = core.run(getClassesToTest());

        System.exit(result.wasSuccessful() ? 0 : 1);
    }

    private static Class<?>[] getClassesToTest()
    {
        String tests = System.getProperty("tests");

        if (tests == null) {
            return DEFAULT_CLASSES_TO_TEST;
        }

        ArrayList<Class<?>> classes = new ArrayList<>();
        for (String name : Splitter.on(",").trimResults().omitEmptyStrings().split(tests)) {
            if (!name.contains(".")) {
                name = TEST_PACKAGE + "." + name;
            }
            Class<?> testClass;

            try {
                testClass = Class.forName(name);
            } catch (ClassNotFoundException e) {
                throw fail("Unable to find test class " + name);
            }

            classes.add(testClass);
        }

        if (classes.isEmpty()) {
            throw fail("The 'tests' property was specified but no tests were specified.");
        }

        return classes.toArray(new Class<?>[classes.size()]);
    }

    private static WebDriverFactory createWebDriverFactory()
    {
        String type = System.getProperty("driver", "htmlunit");
        switch (type) {
        case "firefox":
            isSnapshotSupported = true;
            return new WebDriverFactory() {
                @Override
                public WebDriver createDriver()
                {
                    return new FirefoxDriver();
                }
            };
        case "safari":
            isSnapshotSupported = true;
            return new WebDriverFactory() {
                @Override
                public WebDriver createDriver()
                {
                    return new SafariDriver();
                }
            };
        case "chrome":
            isSnapshotSupported = true;
            return new WebDriverFactory() {
                @Override
                public WebDriver createDriver()
                {
                    return new ChromeDriver();
                }
            };
        case "ie":
            isSnapshotSupported = true;
            return new WebDriverFactory() {
                @Override
                public WebDriver createDriver()
                {
                    return new InternetExplorerDriver();
                }
            };
        case "htmlunit":
            isSnapshotSupported = false;
            return new WebDriverFactory() {
                @Override
                public WebDriver createDriver()
                {
                    return new HtmlUnitDriver();
                }
            };
        case "phantomjs":
            isSnapshotSupported = true;

            // Silence GhostDriver logging via Java Logging.
            final InputStream inputStream = Runner.class.getResourceAsStream("/logging.properties");
            try {
                LogManager.getLogManager().readConfiguration(inputStream);
            } catch (IOException e) {
                // Should only happen when build is broken.
                throw new RuntimeException(e);
            }

            // Silence PhantomJS's own logging, which is a little too eager
            // by default.
            final DesiredCapabilities dcap = new DesiredCapabilities();
            String[] phantomArgs = new  String[] {
                "--webdriver-loglevel=NONE"
            };
            dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);

            return new WebDriverFactory() {
                @Override
                public WebDriver createDriver()
                {
                    return new PhantomJSDriver(dcap);
                }
            };
        default:
            throw fail("Unknown driver '" + type + "', should be one of {firefox,safari,chrome,ie,phantomjs,htmlunit}");
        }
    }

    private static File getSnapshotPath()
    {
        String screenshotPath = System.getProperty("screenshot.path");
        if (screenshotPath == null) {
            return null;
        }

        if (!isSnapshotSupported) {
            throw fail("Current driver does not support snapshots (change with driver system property).");
        }

        File path = new File(screenshotPath);

        if (!path.exists()) {
            if (!path.mkdirs()) {
                throw fail("Path " + screenshotPath + " does not exist and could not be created");
            }
        }

        if (!path.isDirectory()) {
            throw fail("Path " + screenshotPath + " is not a directory.");
        }

        return path;
    }


    // Ugly work-around: we pretend to throw RuntimeException to allow caller
    // to pretend to throw returned value.  This tells compiler that following
    // code is never executed.
    private static RuntimeException fail(String message)
    {
        System.err.println(message);
        System.exit(2);
        return new RuntimeException("Unreachable code");
    }
}
