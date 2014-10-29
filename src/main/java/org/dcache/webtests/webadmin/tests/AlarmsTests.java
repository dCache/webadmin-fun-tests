package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.AlarmsPage;
import org.dcache.webtests.webadmin.pages.LoginPage;

/**
 * Tests that focus on the alarms page.
 */
public class AlarmsTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateToAlarmsBeforeLoginRedirectToLoginPage()
    {
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
