package org.dcache.webtests.webadmin.tests;

import org.junit.Ignore;
import org.junit.Test;

import org.dcache.webtests.webadmin.pages.CellServicesPage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests that focus on the Cell Services page
 */
public class CellServicesTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigationBeforeLogin() throws InterruptedException
    {
        overview.navigateToCellServices().assertPageIs(CellServicesPage.class);
    }

    @Ignore("getQuickFindText seems broken")
    @Test
    public void testEnterFilterText()
    {
        CellServicesPage page = overview.navigateToCellServices().assertPageIs(CellServicesPage.class);

        page.typeQuickFindText("xyzzy");

        assertThat(page.getQuickFindText(), equalTo("xyzzy"));
    }

    @Test
    public void testClearFiltersButton()
    {
        CellServicesPage page = overview.navigateToCellServices().assertPageIs(CellServicesPage.class);

        page.typeQuickFindText("xyzzy");
        page.clickClearFiltersButton();

        assertThat(page.getQuickFindText(), equalTo(""));
    }
}
