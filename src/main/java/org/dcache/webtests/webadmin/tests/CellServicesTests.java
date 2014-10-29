package org.dcache.webtests.webadmin.tests;

import org.junit.Ignore;
import org.junit.Test;

import org.dcache.webtests.webadmin.pages.CellServicesPage;
import org.dcache.webtests.webadmin.pages.LoginPage;

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
        CellServicesPage cellServices = overview.navigateToCellServices().assertPageIs(CellServicesPage.class);

        cellServices.assertLoggedOut();
    }

    @Test
    public void testNavigationAfterLogin() throws InterruptedException
    {
        CellServicesPage cellServices = overview.login().navigateToCellServices().assertPageIs(CellServicesPage.class);

        cellServices.assertLoggedIn();
    }

    @Test
    public void testUserActionNavigatesToLogin()
    {
        LoginPage page = overview.navigateToCellServices().clickUserAction().assertPageIs(LoginPage.class);
        page.assertLoggedOut();
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
