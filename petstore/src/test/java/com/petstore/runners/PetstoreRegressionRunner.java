package com.petstore.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue     = {"com.petstore.hooks", "com.petstore.stepdefs"},
        tags     = "@regression",
        plugin   = {
            "pretty",
            "html:target/cucumber-reports/regression.html",
            "json:target/cucumber-reports/regression.json",
            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class PetstoreRegressionRunner extends AbstractTestNGCucumberTests {
}
