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
    private int assumptionFailureCount;

    public PrintStreamReporter(PrintStream out)
    {
        _out = out;
    }

    @Override
    public void onTestFailure(Failure failure)
    {
        Description test = failure.getDescription();
        _out.println("FAILED  " + test.getTestClass().getSimpleName() + ": " + test.getMethodName());
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
        _out.println("IGNORED " + test.getTestClass().getSimpleName() + ": " + test.getMethodName());
    }

    @Override
    public void onTestAssumptionFailed(Failure failure)
    {
        assumptionFailureCount++;
        Description description = failure.getDescription();
        _out.println("UNABLE  " + description.getTestClass().getSimpleName() +
                ": " + description.getMethodName() + " - " + failure.getMessage());
    }

    @Override
    public void onTestSuccess(Description test)
    {
        _out.println("SUCCESS " + test.getTestClass().getSimpleName() + ": " + test.getMethodName());
    }

    @Override
    public void testRunFinished(Result result)
    {

        int successCount = result.getRunCount() - result.getFailureCount();
        boolean haveSuccesses = successCount > 0;
        boolean haveFailures = result.getFailureCount() > 0;
        boolean haveIgnored = result.getIgnoreCount() > 0;
        boolean haveAssumptionFailures = assumptionFailureCount > 0;

        StringBuilder sb = new StringBuilder("\nRan ");
        if (!haveSuccesses && !haveFailures) {
            sb.append("no tests");
        } else {
            sb.append(result.getRunCount()).append(" tests");

            if (!haveFailures) {
                    sb.append(", all successful");
            } else if (!haveSuccesses) {
                    sb.append(", all failed");
            }
        }

        if (haveSuccesses && haveFailures || haveIgnored || haveAssumptionFailures) {
            boolean needComma = false;
            sb.append(" (");
            if (haveSuccesses && haveFailures) {
                sb.append(successCount).append(" succeeded");
                sb.append(", ");
                sb.append(result.getFailureCount()).append(" failed");
                needComma = true;
            }
            if (haveIgnored) {
                if (needComma) {
                    sb.append(", ");
                }
                sb.append(result.getIgnoreCount()).append(" ignored");
                needComma = true;
            }
            if (haveAssumptionFailures) {
                if (needComma) {
                    sb.append(", ");
                }
                sb.append(assumptionFailureCount);
                if (assumptionFailureCount == 1) {
                    sb.append(" was");
                } else {
                    sb.append(" were");
                }
                sb.append(" unable to run");
            }
            sb.append(")");
        }

        sb.append(" in ").append(result.getRunTime()/1000).append(" seconds.");
        _out.println(sb);
    }
}
