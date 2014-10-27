package org.dcache.webtests.webadmin;

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
import java.util.logging.LogManager;

import org.dcache.webtests.drivers.WebDriverFactory;
import org.dcache.webtests.webadmin.tests.AlarmsTests;
import org.dcache.webtests.webadmin.tests.CellServicesTests;
import org.dcache.webtests.webadmin.tests.LoginTests;
import org.dcache.webtests.webadmin.tests.OverviewTests;
import org.dcache.webtests.webadmin.tests.PoolUsageTests;

/**
 *  Start JUnit for the web-admin tests.
 */
public class Runner
{
    public static final String TARGET_URL = System.getProperty("webadmin.url", "http://localhost:2288/");
    public static final WebDriverFactory WEB_DRIVER_FACTORY = createWebDriverFactory();
    public static final File SNAPSHOT_PATH = getSnapshotPath();

    private static boolean isSnapshotSupported;

    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException
    {
        JUnitCore core = new JUnitCore();
        core.addListener(new PrintStreamReporter(System.out));

        String path = System.getProperty("jenkins.path");
        if (path != null) {
            core.addListener(new JenkinsResultReporter(path));
        }

        Result result = core.run(OverviewTests.class, LoginTests.class,
                PoolUsageTests.class, CellServicesTests.class, AlarmsTests.class);

        System.exit(result.wasSuccessful() ? 0 : 1);
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
            System.err.println("Unknown driver '" + type + "', should be one of {firefox,safari,chrome,ie,phantomjs,htmlunit}");
            System.exit(2);
        }
        return null;
    }

    private static File getSnapshotPath()
    {
        String screenshotPath = System.getProperty("screenshot.path");
        if (screenshotPath == null) {
            return null;
        }

        if (!isSnapshotSupported) {
            System.err.println("Current driver does not support snapshots (change with driver system property).");
            System.exit(2);
        }

        File path = new File(screenshotPath);

        if (!path.exists()) {
            if (!path.mkdirs()) {
                System.err.println("Path " + screenshotPath + " does not exist and could not be created");
                System.exit(2);
            }
        }

        if (!path.isDirectory()) {
            System.err.println("Path " + screenshotPath + " is not a directory.");
        }

        return path;
    }
}
