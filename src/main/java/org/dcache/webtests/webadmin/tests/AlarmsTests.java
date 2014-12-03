package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.AlarmsPage;
import org.dcache.webtests.webadmin.pages.LoginPage;

import static org.dcache.webtests.DcacheVersion.before;
import static org.dcache.webtests.Util.httpdDcacheVersion;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assume.assumeThat;

/**
 * Tests that focus on the alarms page.
 */
public class AlarmsTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateToAlarmsBeforeLoginRedirectToLoginPage()
    {
        assumeThat("Skipping known-broken behaviour", httpdDcacheVersion(),
                is(not(before("2.7.0"))));
        LoginPage login = overview.navigateToAlarms().assertPageIs(LoginPage.class);

        login.assertLoggedOut();
    }

    @Test
    public void testNavigateToAlarmsAfterLogin()
    {
        AlarmsPage login = overview.login().navigateToAlarms().assertPageIs(AlarmsPage.class);

        login.assertLoggedIn();
    }
}
