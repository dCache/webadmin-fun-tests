package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public class PoolSelectionSetupPage extends DcachePage
{

    public PoolSelectionSetupPage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("PoolManager (Pool SelectionUnit) Configuration");
    }
}
