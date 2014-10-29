package org.dcache.webtests.webadmin.tests;

import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.openqa.selenium.WebDriver;

import org.dcache.webtests.webadmin.Runner;
import org.dcache.webtests.webadmin.WebDriverRule;
import org.dcache.webtests.webadmin.pages.OverviewPage;

/**
 * The common aspect to all test-cases.
 */
public class AbstractWebDriverTests
{
    protected WebDriver driver;
    protected OverviewPage overview;

    @Rule
    public MethodRule screenshot = new WebDriverRule();

    public void acceptDriver(WebDriver driver)
    {
        this.driver = driver;
        driver.get(Runner.TARGET_URL);
        overview = new OverviewPage(driver);
    }
}
