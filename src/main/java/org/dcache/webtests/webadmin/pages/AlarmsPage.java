package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.WebDriver;

/**
 * The PageObject for the Alarms page.
 */
public class AlarmsPage extends DcachePage
{

    public AlarmsPage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("DCache Alarm Page");
    }
}
