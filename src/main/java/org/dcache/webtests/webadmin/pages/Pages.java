package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

import static org.junit.Assert.fail;

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

        if (PoolUsagePage.isCurrentPage(driver)) {
            return new PoolUsagePage(driver);
        }

        if (PoolQueuesPage.isCurrentPage(driver)) {
            return new PoolQueuesPage(driver);
        }

        if (AlarmsPage.isCurrentPage(driver)) {
            return new AlarmsPage(driver);
        }

        if (PoolQueuePlotsPage.isCurrentPage(driver)) {
            return new PoolQueuePlotsPage(driver);
        }

        if (PoolgroupsPage.isCurrentPage(driver)) {
            return new PoolgroupsPage(driver);
        }

        if (TapeTransferQueuePage.isCurrentPage(driver)) {
            return new TapeTransferQueuePage(driver);
        }

        if (ActiveTransfersPage.isCurrentPage(driver)) {
            return new ActiveTransfersPage(driver);
        }

        if (BillingPlotsPage.isCurrentPage(driver)) {
            return new BillingPlotsPage(driver);
        }

        if (PoolSelectionSetupPage.isCurrentPage(driver)) {
            return new PoolSelectionSetupPage(driver);
        }

        fail("Failed to identify page: title='" + driver.getTitle() + "', URL=" + driver.getCurrentUrl());

        throw new RuntimeException("Unreachable statement.");
    }
}
