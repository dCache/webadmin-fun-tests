package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The PageObject associated with the login page.
 */
public class LoginPage extends DcachePage
{
    private static final By USERNAME_LOCATOR = By.id("login.username");
    private static final By PASSWORD_LOCATOR = By.id("login.password");
    private static final By REMEMBER_ME_LOCATOR = By.id("login.rememberme");
    private static final By LOGIN_BUTTON_LOCATOR = By.id("login.submit");
    private static final By RESET_BUTTON_LOCATOR = By.id("login.reset");
    private static final By CERTIFICATE_LOGIN_BUTTON_LOCATOR = By.id("login.certsignin");

    public LoginPage(WebDriver driver)
    {
        super(driver);

        if (!isCurrentPage(driver)) {
            throw new IllegalStateException("This is not the login page");
        }
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Login");
    }

    public LoginPage typeUsername(String username)
    {
        _driver.findElement(USERNAME_LOCATOR).sendKeys(username);
        return this;
    }

    public String getUsername()
    {
        return _driver.findElement(USERNAME_LOCATOR).getText();
    }

    public LoginPage typePassword(String password)
    {
        _driver.findElement(PASSWORD_LOCATOR).sendKeys(password);
        return this;
    }

    public String getPassword()
    {
        return _driver.findElement(PASSWORD_LOCATOR).getText();
    }

    public LoginPage setRememberMe(boolean remember)
    {
        WebElement rememberCheckbox = _driver.findElement(REMEMBER_ME_LOCATOR);
        if (rememberCheckbox.isSelected() != remember) {
            rememberCheckbox.click();
        }
        return this;
    }

    public boolean isRememberMeSet()
    {
        return _driver.findElement(REMEMBER_ME_LOCATOR).isSelected();
    }

    public DcachePage submitUsernamePasswordLoginExpectingFailure()
    {
        _driver.findElement(LOGIN_BUTTON_LOCATOR).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage submitUsernamePasswordLogin()
    {
        _driver.findElement(LOGIN_BUTTON_LOCATOR).click();
        return Pages.currentPage(_driver);
    }

    public DcachePage reset()
    {
        _driver.findElement(RESET_BUTTON_LOCATOR).click();
        return Pages.currentPage(_driver);
    }
}
