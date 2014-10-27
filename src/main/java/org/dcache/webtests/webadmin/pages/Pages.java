package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 * Utility methods.
 */
public class Pages
{
    private Pages()
    {
        // prevent instantiation.
    }

    /**
     * Return the most specific PageObject for the current page.
     */
    public static DcachePage currentPage(WebDriver driver)
    {
        if (LoginPage.isCurrentPage(driver)) {
            return new LoginPage(driver);
        }

        if (OverviewPage.isCurrentPage(driver)) {
            return new OverviewPage(driver);
        }

        if (CellServicesPage.isCurrentPage(driver)) {
            return new CellServicesPage(driver);
        }

        if (AlarmsPage.isCurrentPage(driver)) {
            return new AlarmsPage(driver);
        }

        if (PoolUsagePage.isCurrentPage(driver)) {
            return new PoolUsagePage(driver);
        }

        throw new IllegalStateException("Unknown page");
    }
}
