package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * PageObject for the overview or home page.
 */
public class OverviewPage extends DcachePage
{
    private static final By LOGIN_LINK = By.xpath("//a[img[@class='login']]");
    private static final By LOGOUT_LINK = By.xpath("//a[img[@class='logout']]");
    private static final By FEEDBACK_PANEL = By.cssSelector("ul.feedbackPanel");

    public OverviewPage(WebDriver driver)
    {
        super(driver);

        if (!isCurrentPage(driver)) {
            throw new IllegalStateException("This is not the overview page");
        }
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("dCache Overview");
    }

    public DcachePage clickLogin()
    {
        _driver.findElement(LOGIN_LINK).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage clickLogout()
    {
        _driver.findElement(LOGOUT_LINK).click();
        return Pages.currentPage(_driver);
    }

    public String getFeedback()
    {
        return _driver.findElement(FEEDBACK_PANEL).getText();
    }
}
