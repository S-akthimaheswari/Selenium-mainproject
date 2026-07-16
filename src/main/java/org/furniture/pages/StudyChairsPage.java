package org.furniture.pages;

import utils.CommonMethods;
import utils.LoggerManager;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StudyChairsPage {

    WebDriver driver;
    WebDriverWait wait;
    CommonMethods cm;

    public StudyChairsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
         cm = new CommonMethods(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    // Search Box
    @FindBy(id = "searchInput")
    WebElement searchBox;

    // Sort By Dropdown
    @FindBy(xpath = "//span[contains(text(),'Sort By')]")
    WebElement sortByDropdown;

    // Product Names
    @FindBy(xpath = "//h2[contains(@class,'XxwSy')]")
    List<WebElement> productNames;

    @FindBy(xpath = "//div[contains(text(),'₹')]")
    List<WebElement> productPrices;

    @FindBy(xpath="//span[contains(text(),'OFF')]")
    List<WebElement> discountPercentages;

    // Search Study Chairs
    public void searchStudyChairs() {
        LoggerManager.info("Waiting for search box");
        cm.closePopup();
        cm.waitForVisibility(searchBox);
        LoggerManager.info("Clicking search box");
        searchBox.click();
        LoggerManager.info("Typing Study Chairs");
        searchBox.clear();
        searchBox.sendKeys("Study Chairs");
        LoggerManager.info("Pressing Enter");
        searchBox.sendKeys(Keys.ENTER);
        LoggerManager.info("Waiting for Study Chairs page");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h1[contains(text(),'Study Chairs')]")),
                ExpectedConditions.titleContains("Study Chairs")
        ));
    }

    public boolean isStudyChairsPageDisplayed() {
        return !driver.findElements(
                        By.xpath("//h1[contains(text(),'Study Chairs')]"))
                .isEmpty();
    }

    public int getStudyChairsCount() {
        cm.closePopup();
        WebElement countElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("span.PpQnM")
                )
        );
        wait.until(driver -> {
            String text = countElement.getText()
                    .replaceAll("[^0-9]", "");
            if (text.isEmpty()) {
                return false;
            }
            return Integer.parseInt(text) > 0;
        });
        String countText = countElement.getText();
        LoggerManager.info("Study Chairs Count : " + countText);
        return Integer.parseInt(countText.replaceAll("[^0-9]", ""));
    }

    public void clickSortBy() {
        cm.closePopup();
        LoggerManager.info("Clicking Sort By");
        cm.click(sortByDropdown);
    }

    public void selectPopularity() {
        cm.closePopup();
        LoggerManager.info("Selecting Popularity");
        WebElement popularity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Popularity')]")));
        popularity.click();
        LoggerManager.info("Popularity selected successfully");
    }

    public List<String> getTopThreeProducts() {
        cm.closePopup();
        wait.until(ExpectedConditions.visibilityOfAllElements(productNames));
        return productNames.stream()
                .filter(WebElement::isDisplayed)
                .limit(3)
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void selectDiscountHighToLow() {
        LoggerManager.info("Selecting Discount High To Low");
        WebElement discount = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(),'Discount High to Low')]")));

        discount.click();
        LoggerManager.info("Discount High To Low selected successfully");
    }

    public void printTopFiveDiscountProducts() {
        cm.closePopup();
        wait.until(ExpectedConditions.visibilityOfAllElements(productNames));
        int count = Math.min(5, productNames.size());
        for(int i = 0; i < count; i++) {
            String name = productNames.get(i).getText();
            String price = productPrices.get(i).getText();
            String discount = discountPercentages.get(i).getText();
            LoggerManager.info(
                    "Product " + (i + 1)
                            + " | Name : " + name
                            + " | Price : " + price
                            + " | Discount : " + discount
            );
        }
    }
    public int getTopFiveProductsCount() {
        return Math.min(5, productNames.size());
    }
}