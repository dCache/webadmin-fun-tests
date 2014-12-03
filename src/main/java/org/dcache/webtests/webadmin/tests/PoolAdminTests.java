package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.PoolAdminPage;

import static org.dcache.webtests.DcacheVersion.before;
import static org.dcache.webtests.Util.httpdDcacheVersion;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assume.assumeThat;

/**
 *
 */
public class PoolAdminTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLoginRedirectToLogin()
    {
        assumeThat("Skipping known-broken behaviour", httpdDcacheVersion(),
                is(not(before("2.7.0"))));
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
