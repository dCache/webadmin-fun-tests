package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.refreshed;
import static org.junit.Assume.*;

/**
 *
 */
public class CellAdminPage extends DcachePage
{
    private static final By SELECT_DOMAIN_LOCATOR = By.id("celladmin.command.domain");
    private static final By SELECT_CELL_LOCATOR = By.id("celladmin.command.cell");
    private static final By COMMAND_TEXT_LOCATOR = By.id("celladmin.command.text");
    private static final By COMMAND_RESPONSE_LOCATOR = By.id("celladmin.command.response");
    private static final By COMMAND_RECEIVER_LOCATOR = By.id("celladmin.command.receiver");
    private static final By COMMAND_SUBMIT_LOCATOR = By.id("celladmin.command.submit");

    public CellAdminPage(WebDriver driver)
    {
        super(driver);
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Cell Admin");
    }

    private Select getDomainSelect()
    {
        return new Select(_driver.findElement(SELECT_DOMAIN_LOCATOR));
    }

    private Select getCellSelect()
    {
        return new Select(_driver.findElement(SELECT_CELL_LOCATOR));
    }

    public List<String> getDomains()
    {
        List<String> domains = new ArrayList<>();

        for (WebElement option: getDomainSelect().getOptions()) {
            domains.add(option.getText());
        }

        return domains;
    }

    public List<String> getCells()
    {
        List<String> cells = new ArrayList<>();

        for (WebElement option: getCellSelect().getOptions()) {
            cells.add(option.getText());
        }

        return cells;
    }

    public void setDomain(String domain)
    {
        assumeTrue("Cannot run this test with htmlunit",
                !(_driver instanceof HtmlUnitDriver));

        getDomainSelect().selectByVisibleText(domain);

        // Updating cellAdminDomain triggers an AJAX event that updates the
        // list of available cells.  We must wait for this to complete to
        // avoid race conditions.
        WebDriverWait wait = new WebDriverWait(_driver, 10);
        wait.until(refreshed(presenceOfElementLocated(SELECT_CELL_LOCATOR)));
    }

    public void setCell(String cell)
    {
        getCellSelect().selectByVisibleText(cell);
    }

    public void sendCommand(String command)
    {
        WebElement textElement = _driver.findElement(COMMAND_TEXT_LOCATOR);
        textElement.sendKeys(command);
        _driver.findElement(COMMAND_SUBMIT_LOCATOR).click();
    }

    public String getResponseHeading()
    {
        return _driver.findElement(COMMAND_RECEIVER_LOCATOR).getText();
    }

    public String getResponse()
    {
        return _driver.findElement(COMMAND_RESPONSE_LOCATOR).getText();
    }
}
