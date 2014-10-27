package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public class PoolUsagePage extends DcachePage
{
    public PoolUsagePage(WebDriver driver)
    {
        super(driver);

        if (!isCurrentPage(driver)) {
            throw new IllegalStateException("This is not the Pool Usage page");
        }
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Pool Usage");
    }
}
