package com.petstore.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtentReportManager {

    private static final Logger log = LoggerFactory.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

    private ExtentReportManager() {}

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = "target/extent-reports/PetstoreReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Petstore API Test Report");
            spark.config().setReportName("Petstore REST Assured + TestNG + BDD");
            spark.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework",   "REST Assured + TestNG + Cucumber");
            extent.setSystemInfo("Base URL",    "https://petstore.swagger.io/v2");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Author",      "QA Team");
            log.info("ExtentReports initialised at {}", reportPath);
        }
        return extent;
    }

    public static ExtentTest getTest()           { return currentTest.get(); }
    public static void setTest(ExtentTest test)  { currentTest.set(test); }
    public static void removeTest()              { currentTest.remove(); }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
            log.info("ExtentReports flushed");
        }
    }
}
