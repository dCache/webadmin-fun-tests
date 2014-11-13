package org.dcache.webtests.webadmin.pages;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        return new Select(_driver.findElement(By.name("cellAdminDomain")));
    }

    private Select getCellSelect()
    {
        return new Select(_driver.findElement(By.name("cellAdminCell")));
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

        Select select = new Select(_driver.findElement(By.name("cellAdminDomain")));
        select.selectByVisibleText(domain);

        // Updating cellAdminDomain triggers an AJAX event that updates the
        // list of available cells.  We must wait for this to complete to
        // avoid race conditions.
        WebDriverWait wait = new WebDriverWait(_driver, 10);
        wait.until(refreshed(presenceOfElementLocated(By.name("cellAdminCell"))));
    }

    public void setCell(String cell)
    {
        Select select = new Select(_driver.findElement(By.name("cellAdminCell")));
        select.selectByVisibleText(cell);
    }

    public void sendCommand(String command)
    {
        WebElement textElement = _driver.findElement(By.name("commandText"));
        textElement.sendKeys(command);
        _driver.findElement(By.name(":submit")).click();
    }

    public String getResponseHeading()
    {
        return _driver.findElement(By.cssSelector("h2")).getText();
    }

    public String getResponse()
    {
        return _driver.findElement(By.cssSelector("div.output span")).getText();
    }
}
