package org.furniture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.CommonMethods;
import utils.ExtentReportManager;
import utils.LoggerManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    WebDriver driver;
    CommonMethods commonMethods;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        commonMethods = new CommonMethods(driver);
    }

    @FindBy(xpath = "//span[normalize-space()='New Arrivals']")
    WebElement newArrivalsMenu;

    @FindBy(xpath = "//div[contains(@id,'category-menu')]")
    WebElement newArrivalsDropdown;

    @FindBy(xpath = "//a[text()='Terra Collection']")
    WebElement terraCollection;

    @FindBy(xpath = "//a[text()='Terra Collection']//ancestor::li")
    WebElement terraSection;

    @FindBy(xpath = "//a[text()='Terra Collection']//ancestor::li//ul//a")
    List<WebElement> terraItems;

    @FindBy(xpath = "//a[text()='Terra Collection']/ancestor::li//a[text()='Bedroom']")
    WebElement terraBedroom;

    // Hover on New Arrivals
    public void hoverOnNewArrivals() {
        commonMethods.closePopup();
        LoggerManager.info("Waiting for New Arrivals menu");
        commonMethods.waitForVisibility(newArrivalsMenu);
        LoggerManager.info("Hovering on New Arrivals menu");
        ExtentReportManager.getTest().info("Hovering on New Arrivals");
        Actions actions = new Actions(driver);
        actions.moveToElement(newArrivalsMenu).pause(Duration.ofSeconds(2)).perform();
        LoggerManager.info("Hover action completed");
        ExtentReportManager.getTest().info("Hover completed successfully");
    }

    // Check dropdown is displayed
    public boolean isNewArrivalsDropdownDisplayed() {
        commonMethods.closePopup();
        LoggerManager.info("Validating New Arrivals dropdown visibility");
        ExtentReportManager.getTest().info("Checking dropdown visibility");
        commonMethods.waitForVisibility(newArrivalsDropdown);
        LoggerManager.info("Dropdown container is visible");
        commonMethods.waitForVisibility(terraCollection);
        LoggerManager.info("Terra Collection is visible inside dropdown");
        boolean result = newArrivalsDropdown.isDisplayed()  && terraCollection.isDisplayed();
        LoggerManager.info("Final dropdown validation result: " + result);
        ExtentReportManager.getTest().info("Dropdown displayed: " + result);
        return result;
    }

    // Get all sub-menu items under Terra Collection
    public List<String> getTerraCollectionItems() {
        commonMethods.closePopup();
        List<String> itemsText = new ArrayList<>();
        LoggerManager.info("Capturing Terra Collection submenu items");
        ExtentReportManager.getTest().info("Fetching Terra Collection items");
        commonMethods.waitForVisibility(terraSection);
        for (WebElement item : terraItems) {
            String text = item.getText().trim();
            LoggerManager.info("Found item: " + text);
            itemsText.add(text);
        }
        return itemsText;
    }

    // Click Terra Collection
    public void clickTerraCollection() {
        commonMethods.closePopup();
        LoggerManager.info("Waiting for Terra Collection option");
        commonMethods.click(terraCollection);
        LoggerManager.info("Clicking Terra Collection");
        ExtentReportManager.getTest().info("Clicking Terra Collection");
    }

    // Validate Terra Collection page
    public boolean isTerraCollectionPageDisplayed() {
        commonMethods.closePopup();
        LoggerManager.info("Validating Terra Collection page");
        String currentUrl = driver.getCurrentUrl();
        LoggerManager.info("Current URL: " + currentUrl);
        return currentUrl.contains("new-terra-collection");
    }

    // Click Terra Bedroom
    public void clickTerraBedroom() {
        commonMethods.closePopup();
        LoggerManager.info("Waiting for Terra Bedroom option");
        commonMethods.click(terraBedroom);
        LoggerManager.info("Clicking Terra Collection -> Bedroom");
        ExtentReportManager.getTest().info("Clicking Terra Bedroom");
    }

    // Validate Terra Bedroom page navigation
    public boolean isTerraBedroomPageDisplayed() {
        commonMethods.closePopup();
        LoggerManager.info("Validating navigation to Terra Bedroom page");
        String currentUrl = driver.getCurrentUrl();
        LoggerManager.info("Current URL: " + currentUrl);
        return currentUrl.contains("terra-bedroom-collection");
    }
}