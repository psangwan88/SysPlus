package org.pages;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.factory.BaseClass;
import org.testng.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends BaseClass {

    Page page;
    String xpath_home = "//a[@title='home']";
    public HomePage(Page page){
       this.page = page;
    }

    public void searchContent(String content){
        clickSearchIcon();
        enterSearchText(content);
        clickSearchButton();
    }
    public void clickSearchIcon(){
        page.locator("//a[@href='#subnav-search-desktop-top']//span[@class='icon-search bhc-icon-search-rounded']").click();
    }
    public void enterSearchText(String content){
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Type to Search")).click();
        Locator locator = page.locator("#search-field");
        locator.last().fill(content);
    }
    public void clickSearchButton(){
        page.locator("//nav[@id='subnav-search-desktop-top']//button[@type='submit'][normalize-space()='Search']").click();
    }
    public void clickLocateCenter(){
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Find a Center")).nth(1).click();
    }

    public void validatedSearchedFirstItem(String expected){
        List<String> list = getSearchedResultsHeadings();
        String allResults = "";
        for (String text : list) {
            allResults += text + ",";
        }
        String actualFirst = list.get(0);
        Assert.assertEquals(actualFirst, expected, "Validating first searched result, all results " + allResults);
    }

    public List<String> getSearchedResultsHeadings(){
        page.waitForSelector("//*[@class='search-result']//h3");
        List<String> resultList = page.locator("//*[@class='search-result']//h3").allInnerTexts();
        for (String text : resultList) {
            System.out.println("--" + text);
        }
        return resultList;
    }
}
