package org.furniture.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.LoggerManager;

import java.time.Duration;

public class BookshelvesPage {

    WebDriver driver;
    WebDriverWait wait;

    public BookshelvesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // SEARCH BOX
    @FindBy(id = "searchInput")
    WebElement searchBox;

    // Result title
    @FindBy(xpath = "//h1[contains(text(),'Bookshelves')]")
    WebElement title;

    //  All Filters button
    @FindBy(className = "qJoGr")
    WebElement allFiltersBtn;

    //  Price section
    @FindBy(xpath = "//div[@aria-label='Price']")
    WebElement priceSection;

    // Max price input (IMPORTANT)
    @FindBy(xpath = "//input[@type='text' and contains(@aria-label,'Maximum')]")
    WebElement maxPriceInput;

    // Apply filter button
    @FindBy(xpath = "//button[.//text()='Apply' or contains(.,'Apply')]")
    WebElement applyFilterBtn;

    /*@FindBy(xpath = "//div[@role='checkbox']//div[text()='Open Storage']")
    WebElement openStorageOption;

    @FindBy(xpath = "//div[@role='tab' and @aria-label='Storage Type']")
    WebElement storageTypeSection;*/

    //  Action
    public void searchBookshelves() {

        LoggerManager.info("Waiting for search box");

        wait.until(ExpectedConditions.visibilityOf(searchBox));

        LoggerManager.info("Clicking search box");
        searchBox.click();

        LoggerManager.info("Typing Bookshelves");
        searchBox.sendKeys("Bookshelves");

        LoggerManager.info("Pressing Enter");
        searchBox.sendKeys(Keys.ENTER);
    }

    public boolean isBookshelvesPageDisplayed() {

        wait.until(ExpectedConditions.visibilityOf(title));
        return title.isDisplayed();
    }

    public void applyPriceFilter() {

        LoggerManager.info("Clicking All Filters");

        wait.until(ExpectedConditions.elementToBeClickable(allFiltersBtn));

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", allFiltersBtn);

        LoggerManager.info("All Filters opened");

        LoggerManager.info("Scrolling to Price");

        wait.until(ExpectedConditions.visibilityOf(priceSection));

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", priceSection);

        // Ensure section is active
        priceSection.click();

        LoggerManager.info("Setting max price");

        wait.until(ExpectedConditions.elementToBeClickable(maxPriceInput));

        maxPriceInput.clear();
        maxPriceInput.sendKeys("15000");

        // ✅ IMPORTANT: trigger blur event
        maxPriceInput.sendKeys(Keys.TAB);

        LoggerManager.info("Waiting for UI to update after entering price");

        // small wait to allow UI to apply value internally
        wait.until(ExpectedConditions.attributeContains(maxPriceInput, "value", "15"));

        LoggerManager.info("Clicking Apply button");

        wait.until(ExpectedConditions.elementToBeClickable(applyFilterBtn));

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", applyFilterBtn);

        LoggerManager.info("Price filter applied successfully");
    }

    public void selectOpenStorage() {

        LoggerManager.info("Scrolling inside filter panel to find Storage Type");

        // ✅ Scroll inside panel using JS (IMPORTANT FIX)
        //((JavascriptExecutor) driver).executeScript(
         //       "document.querySelector('div[style*=\"overflow\"]').scrollTop=500"
        //);

        LoggerManager.info("Locating Storage Type section");

        WebElement storageType = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[text()='Storage Type']")
                ));

        storageType.click();

        LoggerManager.info("Storage Type expanded");

        LoggerManager.info("Selecting Open Storage");

        WebElement openStorage = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='checkbox']//div[text()='Open Storage']")
                ));

        openStorage.click();

        LoggerManager.info("Open Storage selected successfully");
    }


    public void applyFilters() {

        LoggerManager.info("Clicking Apply button");

        WebElement applyBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[contains(.,'Apply')]")
                ));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", applyBtn);

        LoggerManager.info("Filters applied successfully");
    }
    public void openFilters() {

        LoggerManager.info("Clicking All Filters button");

        wait.until(ExpectedConditions.elementToBeClickable(allFiltersBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", allFiltersBtn);

        LoggerManager.info("Filters panel opened successfully");
    }
}
