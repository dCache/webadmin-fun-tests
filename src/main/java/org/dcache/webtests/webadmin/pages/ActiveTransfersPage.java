package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public class ActiveTransfersPage extends DcachePage
{

    public ActiveTransfersPage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Active Transfers");
    }
}
