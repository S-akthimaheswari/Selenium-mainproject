package org.furniture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.CommonMethods;
import utils.ExtentReportManager;
import utils.LoggerManager;

public class TerraCollectionPage {
    WebDriver driver;
    CommonMethods cm;

    public TerraCollectionPage(WebDriver driver) {
        this.driver = driver;
        cm = new CommonMethods(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(@href,'/collection/terra-collection')]")
    WebElement discoverAllTerraProducts;

    @FindBy(xpath = "//div[text()='ALL FILTERS']")
    WebElement allFiltersBtn;

    @FindBy(xpath = "//span[text()='Sort']")
    WebElement sortSection;

    @FindBy(xpath = "//div[text()='Price High to Low']")
    WebElement priceHighToLowOption;

    @FindBy(xpath = "//div[@role='button' and @aria-label='Close filter drawer']")
    WebElement closeSidebarBtn;

    // Scroll to Discover all Terra products
    public void scrollToDiscoverAllTerraProducts() {
        cm.closePopup();
        LoggerManager.info("Scrolling to Discover all Terra products link");
        ExtentReportManager.getTest().info("Scrolling to Discover all Terra products link");
        cm.scrollToElement(discoverAllTerraProducts);
    }

    // Click Discover all Terra products
    public void clickDiscoverAllTerraProducts() {
        cm.closePopup();
        LoggerManager.info("Clicking Discover all Terra products");
        ExtentReportManager.getTest().info("Clicking Discover all Terra products");
        cm.click(discoverAllTerraProducts);
    }

    // Switch to new tab
    public void switchToNewTab() {
        String currentWindow = driver.getWindowHandle();
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    // Validate Terra Products page
    public boolean isTerraProductsPageDisplayed() {
        cm.closePopup();
        LoggerManager.info("Validating Terra Collection products page");
        String currentUrl = driver.getCurrentUrl();
        LoggerManager.info("Current URL: " + currentUrl);
        return currentUrl.contains("/collection/terra-collection");
    }

    // Open All Filters
    public void openAllFilters() {
        cm.closePopup();
        LoggerManager.info("Opening All Filters");
        cm.click(allFiltersBtn);
    }

    // Expand Sort section
    public void expandSort() {
        cm.closePopup();
        LoggerManager.info("Expanding Sort section");
        cm.click(sortSection);
    }

    // Select Price High to Low
    public void selectPriceHighToLow() {
        cm.closePopup();
        LoggerManager.info("Selecting Price High to Low");
        cm.click(priceHighToLowOption);
    }

    // Close Filter Sidebar
    public void closeFilterPanel() {
        cm.closePopup();
        LoggerManager.info("Closing Filter and Sort sidebar");
        cm.click(closeSidebarBtn);
    }
}
