package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.OverviewPage;
import org.dcache.webtests.webadmin.pages.PoolUsagePage;

/**
 *
 */
public class PoolUsageTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLogin()
    {
        PoolUsagePage poolUsage = overview.navigateToPoolUsage().assertPageIs(PoolUsagePage.class);

        poolUsage.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        OverviewPage afterLogin = overview.login();
        PoolUsagePage poolUsage = afterLogin.navigateToPoolUsage().assertPageIs(PoolUsagePage.class);

        poolUsage.assertLoggedIn();
    }
}
