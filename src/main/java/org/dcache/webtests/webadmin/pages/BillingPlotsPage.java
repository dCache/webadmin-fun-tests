package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public class BillingPlotsPage extends DcachePage
{

    public BillingPlotsPage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Billing History Plots");
    }
}
