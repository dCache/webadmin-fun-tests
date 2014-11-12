package org.dcache.webtests.webadmin;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.WebDriverException;

/**
 *
 */
public class PrintStreamReporter extends TestResultListener
{
    private final PrintStream _out;

    public PrintStreamReporter(PrintStream out)
    {
        _out = out;
    }

    @Override
    public void onTestFailure(Failure failure)
    {
        Description test = failure.getDescription();
        _out.println("FAILED:  " + test.getTestClass().getSimpleName() + ":" + test.getMethodName());
        Throwable t = failure.getException();
        if (t != null) {
            if (t instanceof WebDriverException) {
                _out.println("    " + t.getMessage().replace("\n", "\n    "));
            } else if (t instanceof RuntimeException) {
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                _out.println("    " + sw.toString().replace("\n", "\n    "));
            } else if (t instanceof AssertionError) {
                _out.println("    " + t.getMessage().replace("\n", "\n    "));
            } else {
                _out.println("    " + t.toString());
            }
        }
    }

    @Override
    public void onTestIgnored(Description test)
    {
        _out.println("IGNORED: " + test.getTestClass().getSimpleName() + ":" + test.getMethodName());
    }

    @Override
    public void onTestSuccess(Description test)
    {
        _out.println("SUCCESS: " + test.getTestClass().getSimpleName() + ":" + test.getMethodName());
    }

    @Override
    public void testRunFinished(Result result)
    {
        _out.println(String.format("\nRan %d tests (%d success, %d ignored, %d failure) in %d seconds.",
                result.getRunCount(),
                (result.getRunCount() - result.getFailureCount()),
                result.getIgnoreCount(), result.getFailureCount(),
                result.getRunTime()/1000));
    }
}
