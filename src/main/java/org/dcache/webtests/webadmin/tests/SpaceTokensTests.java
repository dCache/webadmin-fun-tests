package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.SpaceTokensPage;

/**
 *
 */
public class SpaceTokensTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLogin()
    {
        SpaceTokensPage page = overview.navigateToSpaceTokens().
                assertPageIs(SpaceTokensPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        SpaceTokensPage page = overview.login().navigateToSpaceTokens().
                assertPageIs(SpaceTokensPage.class);

        page.assertLoggedIn();
    }

    @Test
    public void testUserActionNavigatesToLogin()
    {
        LoginPage page = overview.navigateToSpaceTokens().clickUserAction().assertPageIs(LoginPage.class);
        page.assertLoggedOut();
    }
}
