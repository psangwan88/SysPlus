package org.factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.utils.Assertion_Lib;
import org.utils.GenericLib;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

public class BaseClass implements Constants {

    public static JSONObject inputData;
    public Path videoDir = Paths.get(videopath1);
    public Path testvideoDir = Paths.get(videopath2);
    public static String[] folders = new String[] {videopath1,videopath2,traceViewPath};
    public static Properties property;
    public static String browserType;
    public static boolean headless = true;
    public static boolean videoRec = false;
    public static boolean traceView = false;
    public static boolean screenshotFail = true;
    public static boolean screenshotPass = false;
    public static boolean stopOnFail = false;
    public static ExtentReports extent;
    public static ExtentSparkReporter reporter;
    public static int retryCount = 0;


    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(){
        System.out.println("In Before suite");
        property = GenericLib.configReader("config/config.properties");
        inputData = readTestData();
        System.out.println("read input dtaa");
        setConfigParams();
        clearData();
        reportInitialize();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(){
        extent.flush();
        // close any db connection or any other connects if any, send any reports etc to slack or etc
    }

    public void setConfigParams(){
        browserType = System.getProperty("browser", property.getProperty("browser"));
        headless = System.getProperty("headless", property.getProperty("headless")).toLowerCase().equals("true") ? true : false;
        videoRec = System.getProperty("videoRecording", property.getProperty("videoRecording")).toLowerCase().equals("true") ? true : false;
        traceView = System.getProperty("traceViewer", property.getProperty("traceViewer")).toLowerCase().equals("true") ? true : false;
        screenshotPass  = System.getProperty("screenshotonPass", property.getProperty("screenshotonPass")).toLowerCase().equals("true") ? true : false;
        screenshotFail = System.getProperty("screenshotonFail", property.getProperty("screenshotonFail")).toLowerCase().equals("true") ? true : false;
        stopOnFail  = System.getProperty("stopOnFail", property.getProperty("stopOnFail")).toLowerCase().equals("true") ? true : false;

    }


    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method){
        System.out.println(method.getName());
        testList.set(extent.createTest(method.getName())
                .log(Status.INFO, "This is a logging event for test name " + method.getName()));

        DriverFactory factory = new DriverFactory();
        browserList.set(factory.initBrowser(browserType, headless));
        browserContextList.set(browserList.get().newContext(getBrowserContextOptions()));
        browserContextList.get().setDefaultTimeout(wait);
        if(traceView)
            browserContextList.get().tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));

        pageList.set(browserContextList.get().newPage());
        pageList.get().navigate(baseUrl);
        //can be removed from here if we want to accept cookies later
        pageList.get().locator("text=Reject All").last().click();

    }

    public Browser.NewContextOptions getBrowserContextOptions(){
        Browser.NewContextOptions contextOption = new Browser.NewContextOptions();
        contextOption.setGeolocation(0, 0)   // Set a fake geolocation (0, 0 is an invalid location)
                .setPermissions(Arrays.asList(new String[]{"geolocation"}));  // Block geolocation permission
        if(videoRec)
            contextOption.setRecordVideoDir(videoDir);

        return contextOption;
    }
    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method, ITestResult result){
        if(videoRec)
            System.out.println(method.getName() + "  " + pageList.get().video().path().toString());
        if (result.getStatus() == ITestResult.FAILURE && screenshotFail) {
            ExtentTest failure = testList.get().createNode("Errors/Failures");
            failure.log(Status.FAIL,result.getThrowable());
            captureScreensho_step();
        }

        if(traceView)
            browserContextList.get().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(traceViewPath + method.getName()+".zip")));
        browserContextList.get().close();
        browserList.get().close();
        if(videoRec)
            moveVideoFile(method.getName());

        extent.flush();
    }


    public void moveVideoFile(String fileName){
        File[] files = videoDir.toFile().listFiles();
        File videoFile = files[0];
        File renamedFile = new File(testvideoDir.toFile(), fileName + ".webm");
        try {
            Files.move(videoFile.toPath(), renamedFile.toPath());
        }
        catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }
    }
    public JSONObject readTestData(){
        String dataPath = dataFile;
        if(property.getProperty("dataFile") != null)
            dataPath = property.getProperty("dataFile");
        return GenericLib.getTestData(dataFile);
    }
    public boolean clearDownloadVideos(File folder){
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

    public void clearData(){
        clearDownloadVideos(new File(reports));
        for (String fol: folders
        ) {
            File folder = new File(fol);
            folder.mkdir();
        }
    }

    public void wait(int millisec){
        try {
            pageList.get().wait(millisec);
        }
        catch (Exception e) {}
    }


    public void captureScreenshot() {
        String screenshotPath = "screenshot_" + System.currentTimeMillis() + ".png";
        pageList.get().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(reports + "/" + screenshotPath)));
        testList.get().addScreenCaptureFromPath(screenshotPath);
    }

    public void captureScreensho_step() {
        String screenshotPath = "screenshot_" + System.currentTimeMillis() + ".png";
        pageList.get().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(reports + "/" + screenshotPath)));
        testList.get().fail("Some Failure is there, check previous logs", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }

    public void reportInitialize(){
        extent = new ExtentReports();
        reporter = new ExtentSparkReporter("reports/Spark.html")
                .viewConfigurer()
                .viewOrder()
                .as(new ViewName[] {
                        ViewName.DASHBOARD,
                        ViewName.TEST,
                        ViewName.CATEGORY,
                        ViewName.AUTHOR,
                        ViewName.DEVICE,
                        ViewName.EXCEPTION,
                        ViewName.LOG
                })
                .apply();

        extent.attachReporter(reporter);
    }

    public void randomWait(){
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(5) *1000;
        try {
            Thread.sleep(rand_int1);
            System.out.println("waiting for " + rand_int1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void assertEquals(Object expected, Object actual, String message){
        Assertion_Lib.getInstance().assertEqual(pageList.get(), testList.get(),
                 expected,  actual, message,
                 screenshotPass,  screenshotFail,  stopOnFail);
    }
}
