package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.TapeTransferQueuePage;

/**
 *
 */
public class TapeTransferQueueTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLogin()
    {
        TapeTransferQueuePage page = overview.navigateToTapeTransferQueue().
                assertPageIs(TapeTransferQueuePage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        TapeTransferQueuePage page = overview.login().navigateToTapeTransferQueue().
                assertPageIs(TapeTransferQueuePage.class);

        page.assertLoggedIn();
    }
}
