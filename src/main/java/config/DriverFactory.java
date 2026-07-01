package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import utils.ConfigReader;
import utils.LoggerManager;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver initDriver(String browserParam) {

        String browser = (browserParam != null)
                ? browserParam
                : ConfigReader.getProperty("browser");

        LoggerManager.info("Initializing browser: " + browser);

        switch (browser.toLowerCase()) {

            /*case "chrome":
                driver = new ChromeDriver();
                LoggerManager.info("Chrome browser launched");
                break;

            case "edge":
                driver = new EdgeDriver();
                LoggerManager.info("Edge browser launched");
                break; */
            case "chrome":
                WebDriverManager.chromedriver().setup();   // ✅ ADD THIS
                driver = new ChromeDriver();
                LoggerManager.info("Chrome browser launched");
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();    // ✅ ADD THIS
                driver = new EdgeDriver();
                LoggerManager.info("Edge browser launched");
                break;

            default:
                throw new RuntimeException("Invalid browser: " + browser);
        }

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicitWait")))
        );

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("pageLoadTimeout")))
        );

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}