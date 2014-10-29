package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.CellAdminPage;
import org.dcache.webtests.webadmin.pages.LoginPage;

/**
 *
 */
public class CellAdminTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLoginRedirectLoginPage()
    {
        LoginPage page = overview.navigateToCellAdmin().assertPageIs(LoginPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNaviagetAfterLogin()
    {
        CellAdminPage page = overview.login().navigateToCellAdmin().
                assertPageIs(CellAdminPage.class);

        page.assertLoggedIn();
    }
}
