package org.dcache.webtests;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

import org.dcache.webtests.webadmin.Runner;

import static com.google.common.base.Throwables.propagate;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNoException;

/**
 * Various utility classes.
 */
public class Util
{
    private static final XPathExpression HTTPD_VERSION =
            xpath("/dCache/domains/domain/cells/cell[@name='httpd']/version/metric[@name='release']");

    private Util()
    {
        // Prevent instantiation
    }

    private static XPathExpression xpath(String expr)
    {
        try {
            return XPathFactory.newInstance().newXPath().compile(expr);
        } catch (XPathExpressionException e) {
            throw propagate(e);
        }
    }

    /**
     * Return information about the dCache version webadmin is running.
     */
    public static DcacheVersion httpdDcacheVersion()
    {
        URI webadminEndpoint = URI.create(Runner.TARGET_URL);

        URI infoEndpoint;

        try {
            infoEndpoint = new URI(webadminEndpoint.getScheme(), null,
                    webadminEndpoint.getHost(), webadminEndpoint.getPort(),
                    "/info/domains", null, null);
        } catch (URISyntaxException e) {
            fail("Problem building info endpoint: " + e.getMessage());
            throw new RuntimeException("Unreachable code");
        }

        URLConnection connection;
        try {
            connection = infoEndpoint.toURL().openConnection();
        } catch (IOException e) {
            fail("Problem contacting info: " + e.getMessage());
            throw new RuntimeException("Unreachable code");
        }


        Document info = null;
        try {
            info = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                    parse(connection.getInputStream());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            fail("Failed to build DOM from info XML: " + e.getMessage());
            throw new RuntimeException("Unreachable code");
        }

        String version;
        try {
            version = (String) HTTPD_VERSION.evaluate(info, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            fail("Failed to extract dCache version from info XML: " + e.getMessage());
            throw new RuntimeException("Unreachable code");
        }

        return DcacheVersion.parse(version);
    }
}
