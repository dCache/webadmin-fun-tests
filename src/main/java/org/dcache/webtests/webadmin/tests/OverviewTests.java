package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.OverviewPage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests focusing on the overview page.
 */
public class OverviewTests extends AbstractWebDriverTests
{
    @Test
    public void testNotLoggedInInitially()
    {
        overview.assertLoggedOut();
    }

    @Test
    public void testUserActionNavigatesToLogin()
    {
        LoginPage page = overview.clickUserAction().assertPageIs(LoginPage.class);
        page.assertLoggedOut();
    }

    @Test
    public void testLoginViaSymbol()
    {
        LoginPage page = overview.clickLogin().assertPageIs(LoginPage.class);
        page.assertLoggedOut();
    }

    @Test
    public void testLogoutWhenLoggedOutDoesntGoAnywhere()
    {
        OverviewPage afterLogout = overview.clickLogout().assertPageIs(OverviewPage.class);

        afterLogout.assertLoggedOut();
        assertThat(afterLogout.getFeedback(), equalTo("Can't log out - You are not logged in!"));
    }
}
