package org.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.factory.BaseClass;
import org.testng.Assert;

import java.util.List;

public class CenterLocatorPage extends BaseClass {
    Page page;
    String text_CenterPlaceHolder = "Address, City, State or Zip";
    String xpath_resultcount = "//span[@class='resultsNumber']";
    String xpath_resultWebCard = "//div[contains(@class,'centerResult infoWindow')]";
    public CenterLocatorPage(Page page){
        this.page = page;
    }

    public void searchCenter(String center){
        logInfo("searching for center "+ center);
        page.getByPlaceholder(text_CenterPlaceHolder).click();
        page.getByPlaceholder(text_CenterPlaceHolder).fill(center);
        page.getByPlaceholder(text_CenterPlaceHolder).press("Enter");
    }

    public int getResultCount(){
        logInfo("getting result count");
        return Integer.parseInt(page.locator(xpath_resultcount).textContent());
    }

    public int getCenterCardsCount(){
        return page.locator(xpath_resultWebCard).count();
    }

    public void selectDistance(){
       // to be implemented in future if we need further tests
    }

    public void verifySearchResultCounts(){
        logInfo("validating result count vs cards shown");
        assertEquals(getResultCount(),getCenterCardsCount(), "Result count vs Result Cards");
    }
    public void validateUrl(String expected){
        logInfo("validating url of new page");
        assertEquals(page.url(),expected, "Validating URL");
    }
}
