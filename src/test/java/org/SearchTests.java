package org;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.factory.BaseClass;
import org.json.simple.JSONObject;
import org.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SearchTests extends BaseClass {

    HomePage homePage;
    @Test
    public void searchContent(){
        // Get test data respective to test
        JSONObject data = (JSONObject) inputData.get("searchContentTest");
        String content = (String)data.get("searchContent");
        // Loggin for test
        ExtentTest node = testList.get().createNode("TestSteps");
        node.log(Status.INFO, "Steps : 1. Click on search Icon. 2. Enter search string. 3. Validate if first search result exactly mataches searchedstring or not");
        // initialize pages instances
        homePage = new HomePage(pageList.get());
        // test case steps and validations
        homePage.searchContent(content);
        homePage.validatedSearchedFirstItem(content);
    }
}
