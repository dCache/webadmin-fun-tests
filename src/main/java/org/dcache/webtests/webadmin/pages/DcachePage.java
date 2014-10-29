package org.dcache.webtests.webadmin.pages;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * A generic PageObject that represents the common aspects of any
 * webadmin page.  This includes the navigation between different pages and
 * the ability to log in or out (top, right corner, just below the bird)
 */
public class DcachePage
{
    private final static By NAV_CELL_SERVICES = By.cssSelector("div[id=nav] a[href*=cellinfo]");
    private final static By NAV_POOL_USAGE = By.cssSelector("div[id=nav] a[href*=usageinfo]");
    private final static By NAV_POOL_QUEUES = By.cssSelector("div[id=nav] a[href*=queueinfo]");
    private final static By NAV_POOL_QUEUE_PLOTS = By.cssSelector("div[id=nav] a[href*=poolqueueplots]");
    private final static By NAV_ALARMS = By.cssSelector("div[id=nav] a[href*=alarms]");

    private final static By LOGIN_NAME = By.cssSelector("span.userLogin b span");
    private final static By LOGIN_LINK = By.cssSelector("span.userLogin a");

    protected final WebDriver _driver;

    public DcachePage(WebDriver driver)
    {
        _driver = driver;
    }

    public DcachePage navigateToCellServices()
    {
        _driver.findElement(NAV_CELL_SERVICES).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToPoolUsage()
    {
        _driver.findElement(NAV_POOL_USAGE).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToPoolQueues()
    {
        _driver.findElement(NAV_POOL_QUEUES).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToPoolQueuePlots()
    {
        _driver.findElement(NAV_POOL_QUEUE_PLOTS).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToAlarms()
    {
        _driver.findElement(NAV_ALARMS).click();
        return Pages.currentPage(_driver);
    }

    public boolean hasLoggedInName()
    {
        return !_driver.findElements(LOGIN_NAME).isEmpty();
    }

    /**
     * Find the current user's login name, as displayed by the current
     * page.  It is an error if there is no login name (e.g., the user isn't
     * currently logged in).
     */
    public String getLoggedInName()
    {
        return _driver.findElement(LOGIN_NAME).getText();
    }

    /**
     * The user-action text: should be either "login" or "logout".
     */
    public String getUserActionLabel()
    {
        return _driver.findElement(LOGIN_LINK).getText();
    }

    /**
     * The action in the top-right corner of the page: either login or
     * logout, depending on whether or not the user is currently logged in.
     */
    public DcachePage clickUserAction()
    {
        _driver.findElement(LOGIN_LINK).click();
        return Pages.currentPage(_driver);
    }

    /**
     * Check that a page has the expected type.
     */
    public <T extends DcachePage> T assertPageIs(Class<T> type)
    {
        assertThat(this, instanceOf(type));
        return type.cast(this);
    }

    /**
     * Utility method to log in as user "admin" from any page.  Asserts
     * that the user is not currently logged in, the login was successful and
     * the browser is navigated to the Overview page.
     */
    public OverviewPage login()
    {
        assertFalse(hasLoggedInName());

        LoginPage loginPage = (this instanceof LoginPage) ? (LoginPage) this :
                clickUserAction().assertPageIs(LoginPage.class);

        loginPage.typeUsername("admin").typePassword("dickerelch").setRememberMe(false);

        return loginPage.submitUsernamePasswordLogin().assertPageIs(OverviewPage.class);
    }

    /**
     * Handy method to assert that page shows we are logged in as a
     * specific user.
     */
    public void assertLoggedIn()
    {
        assertTrue(hasLoggedInName());
        assertThat(getUserActionLabel(), equalTo("logout"));
        assertThat(getLoggedInName(), equalTo("admin"));
    }

    public void assertLoggedOut()
    {
        assertFalse(hasLoggedInName());
        assertThat(getUserActionLabel(), equalTo("login"));
    }
}
