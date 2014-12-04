package org.dcache.webtests;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

import org.dcache.webtests.webadmin.Runner;

import static com.google.common.base.Throwables.propagate;
import static org.junit.Assert.fail;

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
        URI webadmin = URI.create(Runner.TARGET_URL);

        URI info;

        try {
            info = new URI(webadmin.getScheme(), null, webadmin.getHost(),
                    webadmin.getPort(), "/info/domains", null, null);
        } catch (URISyntaxException e) {
            fail("Problem building info endpoint: " + e.getMessage());
            throw new RuntimeException("Unreachable code");
        }

        String version = fetchDcacheVersionWithRetries(info);

        return DcacheVersion.parse(version);
    }

    /**
     * Try for up to 5 minutes: list of domains updated every 2 minutes
     * (single round-trip to topo), list of cells (from each domain)
     * updated every 2 minutes, 1 minute for message round-trip overhead.
     */
    private static String fetchDcacheVersionWithRetries(URI endpoint)
    {
        int attempt=0;
        while (true) {
            String problem;
            try {
                String version = fetchDcacheVersion(endpoint);
                if (version.isEmpty()) {
                    problem = "httpd service not started yet";
                } else {
                    return version;
                }
            } catch (IOException e) {
                problem = "Problem fetching info: " + e.getMessage();
            }

            if (attempt++ < 300) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    fail("Interrupted while waiting for info service to start");
                }
            } else {
                fail(problem);
                throw new RuntimeException("Unreachable code");
            }
        }
    }

    private static String fetchDcacheVersion(URI endpoint) throws IOException
    {
        URLConnection connection = endpoint.toURL().openConnection();

        try {
            Document info = DocumentBuilderFactory.newInstance().
                    newDocumentBuilder().parse(connection.getInputStream());
            return (String) HTTPD_VERSION.evaluate(info);
        } catch (ParserConfigurationException | SAXException e) {
            // Should not happen: info should always send well-formed XML.
            fail("Failed to build DOM from info XML: " + e.getMessage());
            throw new RuntimeException("Unreachable code");
        } catch (XPathExpressionException e) {
            // Should not happen: the XPATH should always evaluate to something.
            fail("Failed to extract dCache version from info XML: " + e.getMessage());
            throw new RuntimeException("Unreachable code");
        }
    }
}
