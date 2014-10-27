package org.dcache.webtests.webadmin;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.dcache.webtests.webadmin.tests.AbstractWebDriverTests;

/**
 * Class that provides the WebDriver and takes a screenshot if the test
 * fails.
 */
public class WebDriverRule implements MethodRule
{
    @Override
    public Statement apply(final Statement base, final FrameworkMethod fm, Object target)
    {
        final AbstractWebDriverTests testcase = (AbstractWebDriverTests) target;

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                WebDriver driver = Runner.WEB_DRIVER_FACTORY.createDriver();

                try {
                    testcase.acceptDriver(driver);
                    base.evaluate();
                } catch (Throwable t) {
                    if (driver instanceof TakesScreenshot && Runner.SNAPSHOT_PATH != null) {
                        String name = String.format("screenshot-%s-%s.png",
                                testcase.getClass().getSimpleName(), fm.getName());
                        takeScreenshot((TakesScreenshot)driver,
                                new File(Runner.SNAPSHOT_PATH, name));
                    }
                    throw t;
                } finally {
                    driver.quit();
                }
            }
        };
    }


    public void takeScreenshot(TakesScreenshot driver, File file)
    {
        try (RandomAccessFile out = new RandomAccessFile(file, "rw")) {
            out.write(driver.getScreenshotAs(OutputType.BYTES));
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + file.getAbsolutePath());
        }
    }
}
