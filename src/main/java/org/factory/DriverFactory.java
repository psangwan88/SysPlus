package org.factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;

public class DriverFactory {
    static Playwright playwright = null;

    public Browser initBrowser(String browserType, boolean headless){
        if(playwright != null)
            playwright = Playwright.create();
        Browser browser;
        BrowserType.LaunchOptions launchOption = new BrowserType.LaunchOptions();
        launchOption.setHeadless(headless);

        switch (browserType.toLowerCase()) {
            case "chrome":
            case "chromium": // Playwright uses "chromium" for Chrome-based browsers
                browser = Playwright.create().chromium().launch(launchOption);
                System.out.println("Launched Chromium browser.");
                break;

            case "firefox":
                browser = Playwright.create().firefox().launch(launchOption);
                System.out.println("Launched Firefox browser.");
                break;

            case "safari":
            case "webkit": // Playwright uses "webkit" for Safari-like browser
                browser = Playwright.create().webkit().launch(launchOption);
                System.out.println("Launched Webkit browser.");
                break;

            default:
                throw new IllegalArgumentException("Invalid browser type: " + browserType);
        }
        return browser;
    }

    public Browser initBrowser(String browserType){
        return initBrowser(browserType,false);
    }

}
