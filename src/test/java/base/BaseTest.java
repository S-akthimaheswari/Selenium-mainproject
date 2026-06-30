package base;

import config.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.LoggerManager;
import java.lang.reflect.Method;

public class BaseTest {

    protected WebDriver driver;

    @BeforeSuite
    public void setupReport() {
        ExtentReportManager.getReportInstance();
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, Method method) {

        LoggerManager.info("===== Test Started =====");

        //  Create report entry
        ExtentReportManager.createTest(method.getName());
        driver = DriverFactory.initDriver(browser);

        String url = ConfigReader.getProperty("url");
        driver.get(url);

        LoggerManager.info("Navigated to: " + url);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReportManager.getTest().pass("Test Passed ✅");
        }
        else if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.getTest().fail(result.getThrowable());
        }
        else if (result.getStatus() == ITestResult.SKIP) {
            ExtentReportManager.getTest().skip("Test Skipped");
        }

        DriverFactory.quitDriver();

        LoggerManager.info("===== Test Finished =====");
    }

    @AfterSuite
    public void flushReport() {
        ExtentReportManager.getReportInstance().flush();
    }
}
