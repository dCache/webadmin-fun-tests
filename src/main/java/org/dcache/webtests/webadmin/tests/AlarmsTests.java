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
        overview.navigateToAlarms().assertPageIs(LoginPage.class);
    }

    @Test
    public void testNavigateToAlarmsAfterLogin()
    {
        overview.login().navigateToAlarms().assertPageIs(AlarmsPage.class);
    }
}
