package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public class PoolgroupsPage extends DcachePage
{

    public PoolgroupsPage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Pool Property Tables");
    }
}
