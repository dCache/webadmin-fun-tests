package org.dcache.webtests.webadmin.tests;

import org.junit.Ignore;
import org.junit.Test;

import org.dcache.webtests.webadmin.pages.LoginPage;
import org.dcache.webtests.webadmin.pages.OverviewPage;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


/**
 *  Tests relating to the Login page.
 */
public class LoginTests extends AbstractWebDriverTests
{
    @Test
    public void testLoginViaSymbol()
    {
        LoginPage page = overview.clickLogin().assertPageIs(LoginPage.class);

        OverviewPage afterLogin = page.login();

        afterLogin.assertLoggedIn();
    }

    @Test
    public void testLoginViaUserAction()
    {
        LoginPage page = overview.clickUserAction().assertPageIs(LoginPage.class);

        OverviewPage afterLogin = page.login();

        afterLogin.assertLoggedIn();
    }

    @Test
    public void testResetButton()
    {
        LoginPage login = overview.clickUserAction().assertPageIs(LoginPage.class);

        login.typeUsername("admin").typePassword("dickerelch").setRememberMe(false);
        login.reset();

        assertThat(login.isRememberMeSet(), is(true));
        assertTrue(login.getUsername().isEmpty());
        assertTrue(login.getPassword().isEmpty());
    }

    @Ignore("Unclear what is correct behaviour")
    @Test
    public void testLoginThenLogin()
    {
        OverviewPage afterLogin = overview.login();

        OverviewPage secondLogin = afterLogin.clickLogin().assertPageIs(OverviewPage.class);

        secondLogin.assertLoggedOut();
    }

    @Test
    public void testLoginThenLogoutViaUserAction()
    {
        OverviewPage afterLogin = overview.login();

        OverviewPage afterLogout = afterLogin.clickUserAction().assertPageIs(OverviewPage.class);

        afterLogout.assertLoggedOut();
    }

    @Test
    public void testLoginThenLogoutViaSymbol()
    {
        OverviewPage afterLogin = overview.login();

        OverviewPage afterLogout = afterLogin.clickLogout().assertPageIs(OverviewPage.class);

        afterLogout.assertLoggedOut();
    }
}
