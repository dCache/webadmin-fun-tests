package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.PoolSelectionSetupPage;

/**
 *
 */
public class PoolSelectionSetupTests extends AbstractWebDriverTests
{

    @Test
    public void testNavigateBeforeLogin()
    {
        PoolSelectionSetupPage page = overview.navigateToPoolSelectionSetup().
                assertPageIs(PoolSelectionSetupPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        PoolSelectionSetupPage page = overview.login().navigateToPoolSelectionSetup().
                assertPageIs(PoolSelectionSetupPage.class);

        page.assertLoggedIn();
    }
}
