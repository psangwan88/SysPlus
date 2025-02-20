package org.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.factory.BaseClass;
import org.testng.Assert;

import java.util.List;

public class HomePage extends BaseClass {

    Page page;
    String xpath_searchIcon = "//a[@href='#subnav-search-desktop-top']//span[@class='icon-search bhc-icon-search-rounded']";
    String id_searchField = "#search-field";
    String text_Search = "Type to Search";
    String text_findCenter = "Find a Center";
    String xpath_HeaderResults = "//*[@class='search-result']//h3";
    String xpath_SearchButton = "//nav[@id='subnav-search-desktop-top']//button[@type='submit'][normalize-space()='Search']";
    public HomePage(Page page){
       this.page = page;
    }

    public void searchContent(String content){
        logInfo("searchig for " + content);
        clickSearchIcon();
        validateSearchField();
        enterSearchText(content);
        clickSearchButton();
    }
    public void clickSearchIcon(){
        logInfo("click on search icon");
        page.locator(xpath_searchIcon).click();
    }
    public void enterSearchText(String content){
        logInfo("enter text in search field");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(text_Search)).click();
        Locator locator = page.locator(id_searchField);
        locator.last().fill(content);
    }
    public void validateSearchField(){
        assertEquals(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(text_Search)).isVisible(), true, "Validating search field is present");
    }
    public void clickSearchButton(){
        logInfo("click on search button");
        page.locator(xpath_SearchButton).click();
    }
    public void clickLocateCenter(){
        logInfo("click on locate center");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text_findCenter)).nth(1).click();
    }

    public void validatedSearchedFirstItem(String expected){
        logInfo("validating if first item in searched list matches " + expected);
        List<String> list = getSearchedResultsHeadings();
        String allResults = "";
        for (String text : list) {
            allResults += text + ",";
        }
        String actualFirst = list.get(0);
        assertEquals(actualFirst, expected, "Validating first searched result");
    }

    public List<String> getSearchedResultsHeadings(){
        page.waitForSelector(xpath_HeaderResults);
        List<String> resultList = page.locator(xpath_HeaderResults).allInnerTexts();
        for (String text : resultList) {
            System.out.println("--" + text);
        }
        return resultList;
    }
}
