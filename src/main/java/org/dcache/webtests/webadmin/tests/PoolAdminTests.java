package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.PoolAdminPage;

/**
 *
 */
public class PoolAdminTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLoginRedirectToLogin()
    {
        LoginPage page = overview.navigateToPoolAdmin().assertPageIs(LoginPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        PoolAdminPage page = overview.login().navigateToPoolAdmin().assertPageIs(PoolAdminPage.class);

        page.assertLoggedIn();
    }
}
