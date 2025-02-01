import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TestStatic {
    public static Playwright playwright;
    public static Browser browser;
    public static Page page;
    public static BrowserContext context;
//    public static void main(String[] args) {
//        try (Playwright playwright = Playwright.create()) {
//            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
//            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
//                    .setGeolocation(0, 0)   // Set a fake geolocation (0, 0 is an invalid location)
//                    .setPermissions(Arrays.asList(new String[]{"geolocation"})));  // Block geolocation permission
//
//            Page page = context.newPage();
//            page.navigate("https://www.brighthorizons.com/");
//            page.locator("text=Reject All").last().click();
//
//
//        }
//    }

    @BeforeMethod
    public void startPageBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions()
                .setGeolocation(0, 0)   // Set a fake geolocation (0, 0 is an invalid location)
                .setPermissions(Arrays.asList(new String[]{"geolocation"})));  // Block geolocation permission

        page = context.newPage();
        page.navigate("https://www.brighthorizons.com/");
        page.locator("text=Reject All").last().click();
    }
    @AfterMethod
    public void closebrowser(){
        browser.close();
    }
    @Test
    public void matchFirstResult(){
        String search = "Employee Education in 2018: Strategies to Watch";
        page.locator("//a[@href='#subnav-search-desktop-top']//span[@class='icon-search bhc-icon-search-rounded']").click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Type to Search")).click();
        Locator locator = page.locator("#search-field");
        locator.last().fill(search);
        page.locator("//nav[@id='subnav-search-desktop-top']//button[@type='submit'][normalize-space()='Search']").click();
        page.waitForSelector("//*[@class='search-result']//h3");
        List<String> resultList = page.locator("//*[@class='search-result']//h3").allInnerTexts();
        for (String text : resultList) {
            System.out.println("--" + text);
        }
        Assert.assertEquals(search, resultList.get(0), "First result matching or not");
    }

    @Test
    public void findCenter(){
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Find a Center")).nth(1).click();
        page.getByPlaceholder("Address, City, State or Zip").click();
        page.getByPlaceholder("Address, City, State or Zip").fill("New York");
        page.getByPlaceholder("Address, City, State or Zip").press("Enter");
        int results = Integer.parseInt(page.locator("//span[@class='resultsNumber']").textContent());
        int resultCounts = page.locator("//div[contains(@class,'centerResult infoWindow')]").count();
        Assert.assertEquals(results,resultCounts, "Result count vs Result Cards");
    }


    public static void main(String[] args) {
        clearData();

    }

    public static void clearData(){
            clearDownloadVideos(new File("reports"));
            File folder = new File("reports");
            folder.mkdir();
    }
    public static boolean clearDownloadVideos(File folder){
        try {
            if (folder.isDirectory()) {
                for (File file : folder.listFiles()) {
                    clearDownloadVideos(file); // Recursive call for subdirectories and files
                }
            }
            return folder.delete(); // Delete the folder or file
        }
        catch(Exception e){
            System.out.println(e.fillInStackTrace());
        }
        return true;
    }
}
