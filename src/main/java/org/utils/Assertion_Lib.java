package org.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import org.testng.Assert;

import java.nio.file.Paths;

public class Assertion_Lib {

    public static Assertion_Lib assertionLib = null;


    public synchronized static Assertion_Lib getInstance(){
        if(assertionLib == null)
            assertionLib = new Assertion_Lib();
        return assertionLib;
    }


    public String captureScreensho_step(Page page) {
        String screenshotPath = "screenshot_" + System.currentTimeMillis() + ".png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("reports" + "/" + screenshotPath)));
        return screenshotPath;
    }
    public boolean assertEqual(Page page, ExtentTest test, Object expected, Object actual, String message, boolean imageOnPass, boolean imageonfail, boolean stopOnFail){
        boolean testFailed = false;
        if(String.valueOf(expected).equals(String.valueOf(actual)))
            if(imageOnPass) {
                String screenshotPath = captureScreensho_step(page);
                test.pass("Passed-" + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
            else
                test.pass("Passed-" + message);
        else { // test has failed
            testFailed = true;
            if(imageonfail) {
                String screenshotPath = captureScreensho_step(page);
                test.fail("Failed-" + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
            else
                test.fail("Failed-" + message);

        if(stopOnFail)
            Assert.assertEquals((String) expected,((String)actual));
        }
        return testFailed;
    }
}
