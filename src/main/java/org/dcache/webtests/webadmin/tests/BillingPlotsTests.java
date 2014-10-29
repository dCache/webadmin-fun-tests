package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.BillingPlotsPage;

/**
 *
 */
public class BillingPlotsTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLogin()
    {
        BillingPlotsPage page = overview.navigateToBillingPlots().
                assertPageIs(BillingPlotsPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        BillingPlotsPage page = overview.login().navigateToBillingPlots().
                assertPageIs(BillingPlotsPage.class);

        page.assertLoggedIn();
    }
}
