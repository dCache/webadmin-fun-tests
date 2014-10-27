package org.dcache.webtests.webadmin;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Produce an XML report of the run suitable for Jenkins to accept.
 *
 * From: https://pzolee.blogs.balabit.com/2012/11/jenkins-vs-junit-xml-format/
 *
 * Here is an example:
 * {@literal
 * <?xml version=”1.0″ encoding=”utf-8″?>
 * <testsuites errors=”1″ failures=”1″ tests=”3″ time=”45″>
 *   <testsuite errors=”1″ failures=”1″ hostname=”localhost” id=”0″
 *       name=”base_test_1″ package=”testdb” tests=”3″
 *       timestamp=”2012-11-15T01:02:29″>
 *     <properties>
 *       <property name=”assert-passed” value=”1″/>
 *     </properties>
 *     <testcase classname=”testdb.directory” name=”001-passed-test” time=”10″/>
 *     <testcase classname=”testdb.directory” name=”002-failed-test” time=”20″>
 *       <failure message=”Assertion FAILED: some failed assert”
 *          type=”failure”> the output of the testcase</failure>
 *     </testcase>
 *     <testcase classname=”package.directory” name=”003-errord-test” time=”15″>
 *       <error message=”Assertion ERROR: some error assert”
 *          type=”error”> the output of the testcase</error>
 *     </testcase>
 *   </testsuite>
 * </testsuites>
 * }
 */
public class JenkinsResultReporter extends TestResultListener
{
    private final File output;
    private final Element testsuites;
    private final Element testsuite;
    private final Document doc;

    private Element currentTestCase;

    private long runStartTime;
    private long testStartTime;

    private long errors;
    private long failures;
    private long tests;

    public JenkinsResultReporter(String output) throws ParserConfigurationException
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        doc = docBuilder.newDocument();
        testsuites = doc.createElement("testsuites");
        doc.appendChild(testsuites);

        testsuite = doc.createElement("testsuite");
        testsuite.setAttribute("name", "webadmin");
        try {
            testsuite.setAttribute("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            testsuite.setAttribute("hostname", "localhost");
        }
        testsuites.appendChild(testsuite);

        this.output = new File(output);
    }

    @Override
    public void testRunStarted(Description description) throws Exception
    {
        runStartTime = System.currentTimeMillis();
        testsuite.setAttribute("timestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()));
    }

    @Override
    public void onTestStart(Description test)
    {
        testStartTime = System.currentTimeMillis();

        currentTestCase = doc.createElement("testcase");
        currentTestCase.setAttribute("classname", test.getClassName());
        currentTestCase.setAttribute("name", test.getMethodName());
        testsuite.appendChild(currentTestCase);
   }

    @Override
    public void onTestFailure(Failure failure)
    {
        Element description;
        if (failure.getException() instanceof AssertionError) {
            failures++;
            description = doc.createElement("failure");
            // FIXME add type attribute, which describes the assertion that failed.
        } else {
            errors++;
            description = doc.createElement("error");
            description.setAttribute("type",
                    failure.getException().getClass().getCanonicalName());
        }
        description.setAttribute("message", failure.getMessage());
        description.setTextContent(failure.getTrace());
        currentTestCase.appendChild(description);
    }

    @Override
    public void onTestIgnored(Description test)
    {
        Element testcase = doc.createElement("testcase");
        testcase.setAttribute("classname", test.getClassName());
        testcase.setAttribute("name", test.getMethodName());
        testcase.appendChild(doc.createElement("skipped"));
        testsuite.appendChild(testcase);
    }

    @Override
    public void onTestFinish(Description test)
    {
        currentTestCase.setAttribute("time", String.valueOf(System.currentTimeMillis() - testStartTime));
        currentTestCase = null;
        tests++;
    }

    @Override
    public void testRunFinished(Result result)
    {
        testsuites.setAttribute("errors", String.valueOf(errors));
        testsuites.setAttribute("failures", String.valueOf(failures));
        testsuites.setAttribute("tests", String.valueOf(tests));
        testsuites.setAttribute("time", String.valueOf(System.currentTimeMillis() - runStartTime));

        testsuite.setAttribute("errors", String.valueOf(errors));
        testsuite.setAttribute("failures", String.valueOf(failures));
        testsuite.setAttribute("tests", String.valueOf(tests));

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(output));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
