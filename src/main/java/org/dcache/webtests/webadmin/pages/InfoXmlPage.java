package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public class InfoXmlPage extends DcachePage
{

    public InfoXmlPage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Info XML Service");
    }
}
