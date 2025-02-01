package org;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.factory.BaseClass;
import org.json.simple.JSONObject;
import org.pages.CenterLocatorPage;
import org.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class LocateCenterTests extends BaseClass {
    public HomePage homePage;
    public CenterLocatorPage clPage;
    @Test
    public void locateCenter(){
        // Get test data respective to test
        JSONObject data = (JSONObject) inputData.get("locateCenterTest");
        String content = (String)data.get("center");
        String url = (String)data.get("url");
        // Loggin for test
        ExtentTest node = testList.get().createNode("TestSteps");
        node.log(Status.INFO, "Steps : 1. Click on locate center. 2. Enter center details. 3. Validate if Result count matches or not");
        // initialize pages instances
        homePage = new HomePage(pageList.get());
        clPage = new CenterLocatorPage(pageList.get());
        // test case steps and validations
        homePage.clickLocateCenter();

        clPage.validateUrl(url);
        clPage.searchCenter(content);
        clPage.verifySearchResultCounts();
    }

}
