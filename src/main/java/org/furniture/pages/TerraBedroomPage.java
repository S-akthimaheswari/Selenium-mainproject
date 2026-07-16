package org.furniture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.CommonMethods;
import utils.ExtentReportManager;
import utils.LoggerManager;

import java.util.List;

public class TerraBedroomPage {
    WebDriver driver;
    CommonMethods commonMethods;

    public TerraBedroomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        commonMethods = new CommonMethods(driver);
    }

    @FindBy(xpath = "//div[text()='ALL FILTERS']")
    WebElement allFiltersBtn;

    @FindBy(xpath = "//div[@aria-label='Primary Material']")
    WebElement primaryMaterialSection;

    @FindBy(xpath = "//div[@aria-label='Bed Size']")
    WebElement bedSizeSection;

    @FindBy(xpath = "//div[@aria-label='Storage Type']")
    WebElement storageTypeSection;

    @FindBy(xpath = "//div[text()='Solid Wood']")
    WebElement solidWoodCheckbox;

    @FindBy(xpath = "//div[text()='King']")
    WebElement kingCheckbox;

    @FindBy(xpath = "//div[text()='Non Storage']")
    WebElement nonStorageCheckbox;

    @FindBy(xpath = "//button[@data-testid='plp-filter-apply-button']")
    WebElement applyFilterBtn;

    @FindBy(xpath = "//span[contains(text(),'Products')]")
    WebElement productCountText;

    @FindBy(xpath = "//div[contains(@data-testid,'product')]")
    List<WebElement> productList;

    // Apply filters
    public void applyFiltersUsingAllFilters() {
        LoggerManager.info("Clicking All Filters");
        commonMethods.click(allFiltersBtn);
        LoggerManager.info("Expanding Primary Material");
        commonMethods.click(primaryMaterialSection);
        LoggerManager.info("Selecting Solid Wood");
        commonMethods.click(solidWoodCheckbox);
        LoggerManager.info("Expanding Bed Size");
        commonMethods.click(bedSizeSection);
        LoggerManager.info("Selecting King");
        commonMethods.click(kingCheckbox);
        LoggerManager.info("Expanding Storage Type");
        commonMethods.click(storageTypeSection);
        LoggerManager.info("Selecting Non Storage");
        commonMethods.click(nonStorageCheckbox);
        LoggerManager.info("Applying filters");
        ExtentReportManager.getTest().info("Applying filters");
        commonMethods.click(applyFilterBtn);
    }

    // Get product count from UI
    public String getProductCountText() {
        LoggerManager.info("Waiting for product count");
        commonMethods.waitForVisibility(productCountText);
        String text = productCountText.getText();
        LoggerManager.info("Product count text: " + text);
        System.out.println(text);
        return text;
    }

    // Get number of displayed products
    public int getProductListSize() {
        LoggerManager.info("Waiting for product list");
        commonMethods.waitForAllElements(productList);
        int size = productList.size();
        LoggerManager.info("Product list size: " + size);
        return size;
    }
}