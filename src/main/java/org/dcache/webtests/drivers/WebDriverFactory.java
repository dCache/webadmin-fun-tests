package org.dcache.webtests.drivers;

import org.openqa.selenium.WebDriver;

/**
 * A class that implements a WebDriverFactory allows generation of WebDriver
 * instances.
 */
public interface WebDriverFactory
{
    /**
     * Create a new WebDriver instance.
     */
    public WebDriver createDriver();
}
