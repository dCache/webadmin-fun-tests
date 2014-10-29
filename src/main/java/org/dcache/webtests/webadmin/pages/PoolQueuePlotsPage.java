package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public class PoolQueuePlotsPage extends DcachePage
{

    public PoolQueuePlotsPage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Pool Queue Activity Plots");
    }
}
