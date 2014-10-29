package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.ActiveTransfersPage;
import org.dcache.webtests.webadmin.pages.LoginPage;

/**
 *
 */
public class ActiveTransfersTests extends AbstractWebDriverTests
{

    @Test
    public void testNavigateBeforeLogin()
    {
        ActiveTransfersPage page = overview.navigateToActiveTransfers().
                assertPageIs(ActiveTransfersPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        ActiveTransfersPage page = overview.login().navigateToActiveTransfers().
                assertPageIs(ActiveTransfersPage.class);

        page.assertLoggedIn();
    }

    @Test
    public void testUserActionNavigatesToLogin()
    {
        LoginPage page = overview.navigateToActiveTransfers().clickUserAction().assertPageIs(LoginPage.class);
        page.assertLoggedOut();
    }
}
