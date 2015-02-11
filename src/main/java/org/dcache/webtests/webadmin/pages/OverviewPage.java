package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

/**
 * PageObject for the overview or home page.
 */
public class OverviewPage extends DcachePage
{
    private static final By LOGIN_LINK = By.id("home.login");
    private static final By LOGOUT_LINK = By.id("home.logout");
    private static final By FEEDBACK_PANEL = By.id("home.feedback");

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
        List<WebElement> foundElements = _driver.findElements(LOGIN_LINK);
        assertThat("page has no clickable login symbol", foundElements, not(empty()));
        foundElements.get(0).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage clickLogout()
    {
        List<WebElement> foundElements = _driver.findElements(LOGOUT_LINK);
        assertThat("page has no clickable logout symbol", foundElements, not(empty()));
        foundElements.get(0).click();
        return Pages.currentPage(_driver);
    }

    public String getFeedback()
    {
        return _driver.findElement(FEEDBACK_PANEL).getText();
    }
}
