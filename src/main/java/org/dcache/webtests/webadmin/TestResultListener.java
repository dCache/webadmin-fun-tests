package org.dcache.webtests.webadmin;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Create events: onTestStart, onTestFailure, onTestIgnore,
 * onTestSuccess, onTestFinish.
 * Ignored tests trigger an onTestIgnored event only.
 * All other tests always trigger onTestStart, then trigger either
 * onTestFailed or onTestSuccess, then onTestFinish is always called.
 */
public class TestResultListener extends RunListener
{
    private boolean _isSuccessful;
    private boolean _isAssumptionFailed;

    @Override
    public void testStarted(Description test)
    {
        _isSuccessful = true;
        _isAssumptionFailed = false;
        onTestStart(test);
    }

    @Override
    public void testFailure(Failure failure)
    {
        _isSuccessful = false;
        onTestFailure(failure);
    }

    @Override
    public void testIgnored(Description test)
    {
        onTestIgnored(test);
    }

    @Override
    public void testFinished(Description test)
    {
        if (_isSuccessful && !_isAssumptionFailed) {
            onTestSuccess(test);
        }
        onTestFinish(test);
    }

    @Override
    public void testAssumptionFailure(Failure failure)
    {
        _isAssumptionFailed = true;
        onTestAssumptionFailed(failure);
    }


    public void onTestStart(Description test)
    {
    }

    public void onTestFailure(Failure failure)
    {
    }

    public void onTestIgnored(Description test)
    {
    }

    public void onTestAssumptionFailed(Failure failure)
    {
    }

    public void onTestSuccess(Description test)
    {
    }

    public void onTestFinish(Description test)
    {
    }
}
