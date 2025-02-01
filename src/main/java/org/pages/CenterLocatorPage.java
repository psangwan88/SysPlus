package org.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.factory.BaseClass;
import org.testng.Assert;

import java.util.List;

public class CenterLocatorPage extends BaseClass {
    Page page;
    String xpath_home = "//a[@title='home']";
    public CenterLocatorPage(Page page){
        this.page = page;
    }

    public void searchCenter(String center){
        page.getByPlaceholder("Address, City, State or Zip").click();
        page.getByPlaceholder("Address, City, State or Zip").fill(center);
        page.getByPlaceholder("Address, City, State or Zip").press("Enter");
    }

    public int getResultCount(){

        return Integer.parseInt(page.locator("//span[@class='resultsNumber']").textContent());
    }

    public int getCenterCardsCount(){
        return page.locator("//div[contains(@class,'centerResult infoWindow')]").count();
    }

    public void selectDistance(){
       // to be implemented in future if we need further tests
    }

    public void verifySearchResultCounts(){
        Assert.assertEquals(getResultCount(),getCenterCardsCount(), "Result count vs Result Cards");
    }
    public void validateUrl(String expected){
        Assert.assertEquals(page.url(),expected, "Validating URL");
    }
}
