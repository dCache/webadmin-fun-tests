package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.PoolgroupsPage;

/**
 *
 */
public class PoolgroupsTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLogin()
    {
        PoolgroupsPage page = overview.navigateToPoolgroups().assertPageIs(PoolgroupsPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        PoolgroupsPage page = overview.login().navigateToPoolgroups().assertPageIs(PoolgroupsPage.class);

        page.assertLoggedIn();
    }

    @Test
    public void testUserActionNavigatesToLogin()
    {
        LoginPage page = overview.navigateToPoolgroups().clickUserAction().assertPageIs(LoginPage.class);
        page.assertLoggedOut();
    }
}
