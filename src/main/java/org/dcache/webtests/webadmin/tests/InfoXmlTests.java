package org.dcache.webtests.webadmin.tests;

import org.junit.Test;

import org.dcache.webtests.webadmin.pages.InfoXmlPage;

/**
 *
 */
public class InfoXmlTests extends AbstractWebDriverTests
{
    @Test
    public void testNavigateBeforeLogin()
    {
        InfoXmlPage page = overview.navigateToInfoXml().
                assertPageIs(InfoXmlPage.class);

        page.assertLoggedOut();
    }

    @Test
    public void testNavigateAfterLogin()
    {
        InfoXmlPage page = overview.login().navigateToInfoXml().
                assertPageIs(InfoXmlPage.class);

        page.assertLoggedIn();
    }
}
