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
        overview.navigateToPoolUsage().assertPageIs(PoolUsagePage.class);
    }

    @Test
    public void testNavigateAfterLogin()
    {
        OverviewPage afterLogin = overview.login();
        afterLogin.navigateToPoolUsage().assertPageIs(PoolUsagePage.class);
    }
}
