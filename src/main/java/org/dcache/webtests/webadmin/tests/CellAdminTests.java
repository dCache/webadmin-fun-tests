package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import java.util.List;

import org.dcache.webtests.webadmin.pages.CellAdminPage;
import org.dcache.webtests.webadmin.pages.LoginPage;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

/**
 *
 */
public class CellAdminTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLoginRedirectLoginPage()
    {
        LoginPage page = overview.navigateToCellAdmin().assertPageIs(LoginPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNaviagetAfterLogin()
    {
        CellAdminPage page = overview.login().navigateToCellAdmin().
                assertPageIs(CellAdminPage.class);

        page.assertLoggedIn();
    }

    @Test
    public void testDomainsContainsDcacheDomain()
    {
        CellAdminPage page = overview.login().navigateToCellAdmin().
                assertPageIs(CellAdminPage.class);

        List<String> domains = page.getDomains();

        assertThat(domains, hasItem("dCacheDomain"));
    }

    @Test
    public void testCellsInitialEmpty()
    {
        CellAdminPage page = overview.login().navigateToCellAdmin().
                assertPageIs(CellAdminPage.class);

        List<String> cells = page.getCells();

        assertThat(cells, contains("Choose One"));
    }

    @Test
    public void testCellsWhenDcacheDomainSelected()
    {
        CellAdminPage page = overview.login().navigateToCellAdmin().
                assertPageIs(CellAdminPage.class);

        page.setDomain("dCacheDomain");

        // FIXME: we're embedding knowledge that should be abstracted out.
        assertThat(page.getCells(), hasItem("LoginBroker"));
    }
}
