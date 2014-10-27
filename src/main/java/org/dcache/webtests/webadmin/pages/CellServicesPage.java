package org.dcache.webtests.webadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * The PageObject for the Cell Services page.
 */
public class CellServicesPage extends DcachePage
{
    private static final By TABLE_CELLNAME = By.xpath("//table/tbody/tr/td[1]");
    private static final By QUICK_FIND_TEXT = By.cssSelector("input.quickfind");
    private static final By QUICK_FIND_CLEAR_BUTTON = By.cssSelector("input.cleanfilters");

    public CellServicesPage(WebDriver driver)
    {
        super(driver);

        if (!isCurrentPage(driver)) {
            throw new IllegalStateException("This is not the Cell Services page");
        }
    }

    public static boolean isCurrentPage(WebDriver driver)
    {
        return driver.getTitle().equals("Services");
    }

    // NB The isDisplayed doesn't appear to work correctly.
    public List<String> getVisibleCellNames()
    {
        List<String> items = new ArrayList();

        for (WebElement td : _driver.findElements(TABLE_CELLNAME)) {
            if (td.isDisplayed()) {
                items.add(td.getText());
            }
        }

        return items;
    }

    public void typeQuickFindText(String text)
    {
        _driver.findElement(QUICK_FIND_TEXT).sendKeys(text);
    }

    // NB The .getText doesn't seem to work.
    public String getQuickFindText()
    {
        return _driver.findElement(QUICK_FIND_TEXT).getText();
    }

    public void clearQuickFindText()
    {
        _driver.findElement(QUICK_FIND_TEXT).clear();
    }

    public void clickClearFiltersButton()
    {
        _driver.findElement(QUICK_FIND_CLEAR_BUTTON).click();
    }
}
