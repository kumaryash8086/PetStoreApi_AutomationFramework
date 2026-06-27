package com.petstore.hooks;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.petstore.utils.ExtentReportManager;
import com.petstore.utils.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetstoreHooks {

    private static final Logger log = LoggerFactory.getLogger(PetstoreHooks.class);
    private final ScenarioContext ctx;

    public PetstoreHooks(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @BeforeAll
    public static void suiteSetup() {
        log.info("========================================");
        log.info("  Petstore API Test Suite - START");
        log.info("  Base URL: https://petstore.swagger.io/v2");
        log.info("========================================");
        ExtentReportManager.getInstance();
    }

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        log.info(">> SCENARIO START: {}", scenario.getName());
        ctx.clear();
        ExtentTest test = ExtentReportManager.getInstance()
                .createTest(scenario.getName());
        ExtentReportManager.setTest(test);
        test.log(Status.INFO, "Scenario: " + scenario.getName());
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        ExtentTest test = ExtentReportManager.getTest();

        if (ctx.getResponse() != null && test != null) {
            try {
                test.log(Status.INFO, "<pre>" + ctx.getResponse().asPrettyString() + "</pre>");
            } catch (Exception e) {
                log.warn("Could not attach response to report: {}", e.getMessage());
            }
        }

        if (scenario.isFailed()) {
            log.error(">> SCENARIO FAILED: {}", scenario.getName());
            if (test != null) test.log(Status.FAIL, "Scenario FAILED: " + scenario.getName());
        } else {
            log.info(">> SCENARIO PASSED: {}", scenario.getName());
            if (test != null) test.log(Status.PASS, "Scenario PASSED");
        }

        ctx.clear();
        ExtentReportManager.removeTest();
    }

    @AfterAll
    public static void suiteTeardown() {
        ExtentReportManager.flush();
        log.info("========================================");
        log.info("  Petstore API Test Suite - END");
        log.info("  Report: target/extent-reports/PetstoreReport.html");
        log.info("========================================");
    }
}