package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public class TapeTransferQueuePage extends DcachePage
{

    public TapeTransferQueuePage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("dCache Dataset Restore Monitor");
    }
}
