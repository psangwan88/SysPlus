package org.factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public interface Constants {
    String dataFile = "TestData/testData.json";
    String baseUrl = "https://www.brighthorizons.com/";
    int wait = 15000; // milliseconds

    String reports = "reports";
    String videopath1 = reports + "/videos/";
    String videopath2 = reports + "/testVideos/";
    String traceViewPath = reports + "/traceview/";
    ThreadLocal<Browser> browserList = new ThreadLocal<>();
    ThreadLocal<BrowserContext> browserContextList = new ThreadLocal<>();
    ThreadLocal<Page> pageList = new ThreadLocal<>();
    ThreadLocal<ExtentTest> testList = new ThreadLocal<>();

    default void logInfo(String message){
        testList.get().log(Status.INFO,message);
    }
}
