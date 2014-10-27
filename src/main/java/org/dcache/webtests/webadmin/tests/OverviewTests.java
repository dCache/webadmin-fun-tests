package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.OverviewPage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Tests focusing on the overview page.
 */
public class OverviewTests extends AbstractWebDriverTests
{
    @Test
    public void testNotLoggedInInitially()
    {
        assertFalse(overview.hasLoggedInName());
        assertThat(overview.getUserActionLabel(), equalTo("login"));
    }

    @Test
    public void testUserActionNavigatesToLogin()
    {
        overview.clickUserAction().assertPageIs(LoginPage.class);
    }

    @Test
    public void testLoginViaSymbol()
    {
        overview.clickLogin().assertPageIs(LoginPage.class);
    }

    @Test
    public void testLogoutWhenLoggedOutDoesntGoAnywhere()
    {
        OverviewPage afterLogout = overview.clickLogout().assertPageIs(OverviewPage.class);

        assertFalse(afterLogout.hasLoggedInName());
        assertThat(afterLogout.getUserActionLabel(), equalTo("login"));
        assertThat(afterLogout.getFeedback(), equalTo("Can't log out - You are not logged in!"));
    }
}
