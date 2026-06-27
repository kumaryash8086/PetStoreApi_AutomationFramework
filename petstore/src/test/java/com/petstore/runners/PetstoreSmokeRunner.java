package com.petstore.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue     = {"com.petstore.hooks", "com.petstore.stepdefs"},
        tags     = "@smoke",
        plugin   = {
            "pretty",
            "html:target/cucumber-reports/smoke.html",
            "json:target/cucumber-reports/smoke.json",
            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class PetstoreSmokeRunner extends AbstractTestNGCucumberTests {
}
