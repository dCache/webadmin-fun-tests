package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.dcache.webtests.DcacheVersion.before;
import static org.dcache.webtests.Util.httpdDcacheVersion;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

/**
 * A generic PageObject that represents the common aspects of any
 * webadmin page.  This includes the navigation between different pages and
 * the ability to log in or out (top, right corner, just below the bird)
 */
public class DcachePage
{
    private final static By NAV_CELL_SERVICES = By.id("nav.cells");
    private final static By NAV_POOL_USAGE = By.id("nav.pools");
    private final static By NAV_POOL_QUEUES = By.id("nav.poolqueues");
    private final static By NAV_POOL_QUEUE_PLOTS = By.id("nav.poolqueueplots");
    private final static By NAV_POOLGROUPS = By.id("nav.poolgroup");
    private final static By NAV_TAPE_TRANSFER_QUEUE = By.id("nav.tapetransfers");
    private final static By NAV_ACTIVE_TRANSFERS = By.id("nav.activetransfers");
    private final static By NAV_BILLING_PLOTS = By.id("nav.billing");
    private final static By NAV_POOL_SELECTION_SETUP = By.id("nav.poolselection");
    private final static By NAV_POOL_ADMIN = By.id("nav.pooladmin");
    private final static By NAV_CELL_ADMIN = By.id("nav.celladmin");
    private final static By NAV_SPACE_TOKENS = By.id("nav.space");
    private final static By NAV_INFO_XML = By.id("nav.info");
    private final static By NAV_ALARMS = By.id("nav.alarms");

    private final static By LOGIN_NAME = By.id("userpannel.username");
    private final static By LOGIN_LINK = By.id("userpannel.action");

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

    public DcachePage navigateToPoolgroups()
    {
        _driver.findElement(NAV_POOLGROUPS).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToTapeTransferQueue()
    {
        _driver.findElement(NAV_TAPE_TRANSFER_QUEUE).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToActiveTransfers()
    {
        _driver.findElement(NAV_ACTIVE_TRANSFERS).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToBillingPlots()
    {
        _driver.findElement(NAV_BILLING_PLOTS).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToPoolSelectionSetup()
    {
        _driver.findElement(NAV_POOL_SELECTION_SETUP).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToPoolAdmin()
    {
        _driver.findElement(NAV_POOL_ADMIN).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToCellAdmin()
    {
        _driver.findElement(NAV_CELL_ADMIN).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToSpaceTokens()
    {
        _driver.findElement(NAV_SPACE_TOKENS).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage navigateToInfoXml()
    {
        // The INFO XML tab is no longer part of webadmin since dCache v2.12.0.
        assumeThat("INFO XML tab was removed", httpdDcacheVersion(),
                is(before("2.12.0")));

        _driver.findElement(NAV_INFO_XML).click();
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
        assertTrue("Page does not show a login name", hasLoggedInName());
        assertThat("Page \"user action\" link (under logo, top-right corner)",
                getUserActionLabel(), equalTo("logout"));
        assertThat("Page logged-in user (under logo, top-right corner)",
                getLoggedInName(), equalTo("admin"));
    }

    public void assertLoggedOut()
    {
        assertFalse("Page shows a login name", hasLoggedInName());
        assertThat("Page \"user action\" link (under logo, top-right corner)",
                getUserActionLabel(), equalTo("login"));
    }
}
