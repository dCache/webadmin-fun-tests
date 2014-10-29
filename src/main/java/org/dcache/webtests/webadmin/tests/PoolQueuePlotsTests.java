package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.PoolQueuePlotsPage;

/**
 *
 */
public class PoolQueuePlotsTests extends AbstractWebDriverTests
{

    @Test
    public void testNavigateBeforeLogin()
    {
        PoolQueuePlotsPage page = overview.navigateToPoolQueuePlots().assertPageIs(PoolQueuePlotsPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        PoolQueuePlotsPage page = overview.login().navigateToPoolQueuePlots().assertPageIs(PoolQueuePlotsPage.class);

        page.assertLoggedIn();
    }

    @Test
    public void testUserActionNavigatesToLogin()
    {
        LoginPage page = overview.navigateToPoolQueuePlots().clickUserAction().assertPageIs(LoginPage.class);
        page.assertLoggedOut();
    }
}
