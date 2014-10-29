package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.OverviewPage;
import org.dcache.webtests.webadmin.pages.PoolQueuesPage;

/**
 *
 */
public class PoolQueuesTests extends AbstractWebDriverTests
{

    @Test
    public void testNavigationBeforeLogin()
    {
        PoolQueuesPage poolQueues = overview.navigateToPoolQueues().assertPageIs(PoolQueuesPage.class);

        poolQueues.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        OverviewPage afterLogin = overview.login();

        PoolQueuesPage poolQueues = afterLogin.navigateToPoolQueues().assertPageIs(PoolQueuesPage.class);

        poolQueues.assertLoggedIn();
    }

    @Test
    public void testUserActionNavigatesToLogin()
    {
        LoginPage page = overview.navigateToPoolQueues().clickUserAction().assertPageIs(LoginPage.class);
        page.assertLoggedOut();
    }
}
