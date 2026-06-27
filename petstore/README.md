# Petstore API Automation Framework

REST Assured + TestNG 7.10.2 + Cucumber 7.18.0 + ExtentReports 5.1.2

## Stack

| Tool | Version | Purpose |
|---|---|---|
| REST Assured | 5.4.0 | API test client |
| TestNG | 7.10.2 | Test runner |
| Cucumber | 7.18.0 | BDD framework |
| ExtentReports | 5.1.2 | HTML reports |
| Jackson | 2.17.1 | JSON serialization |
| JavaFaker | 1.0.2 | Test data generation |
| Logback | 1.5.6 | Logging |

## Structure
```
src/test/
├── java/com/petstore/
│   ├── api/           → PetAPI, StoreAPI, UserAPI
│   ├── config/        → ConfigManager, SpecBuilder
│   ├── constants/     → ApiEndpoints, StatusCodes
│   ├── hooks/         → PetstoreHooks (Before/After)
│   ├── models/        → Request/Response POJOs
│   ├── runners/       → TestRunner, SmokeRunner, RegressionRunner
│   ├── stepdefs/      → Step definitions (Pet, Store, User)
│   └── utils/         → ScenarioContext, ResponseValidator, TestDataFactory, ExtentReportManager
└── resources/
    ├── features/      → pet, store, user .feature files
    ├── config.properties
    ├── extent.properties
    ├── logback.xml
    └── testng*.xml
```

## Run Commands

```bash
# All tests
mvn clean test

# Smoke tests only
mvn clean test -P smoke

# Regression tests only
mvn clean test -P regression

# By tag
mvn clean test -Dcucumber.filter.tags="@pet"
mvn clean test -Dcucumber.filter.tags="@store"
mvn clean test -Dcucumber.filter.tags="@user"
mvn clean test -Dcucumber.filter.tags="@negative"
```

## Reports

After running tests:
- **ExtentReports HTML** → `target/extent-reports/PetstoreReport.html`
- **Cucumber HTML**      → `target/cucumber-reports/cucumber.html`
- **Logs**               → `target/logs/petstore-test.log`

## Eclipse Setup

1. File → Import → Maven → Existing Maven Projects
2. Right-click project → Maven → Update Project (Alt+F5)
3. Run as → TestNG Suite → `src/test/resources/testng.xml`
