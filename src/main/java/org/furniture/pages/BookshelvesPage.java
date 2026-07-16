package org.furniture.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.CommonMethods;
import utils.ConfigReader;
import utils.LoggerManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BookshelvesPage {
    WebDriver driver;
    WebDriverWait wait;
    CommonMethods cm;

    public BookshelvesPage(WebDriver driver) {
        this.driver = driver;
        cm = new CommonMethods(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    // Search Box
    @FindBy(id = "searchInput")
    WebElement searchBox;

    // Result Title
    @FindBy(xpath = "//h1[contains(text(),'Bookshelves')]")
    WebElement title;

    // All Filters Button
    @FindBy(className = "qJoGr")
    WebElement allFiltersBtn;

    // Price Section
    @FindBy(xpath = "//div[@aria-label='Price']")
    WebElement priceSection;

    // Maximum Price Input
    @FindBy(xpath = "//input[@type='text' and contains(@aria-label,'Maximum')]")
    WebElement maxPriceInput;

    // Apply Filter Button
    @FindBy(xpath = "//button[.//text()='Apply' or contains(.,'Apply')]")
    WebElement applyFilterBtn;

//    // Bookshelf names
//    @FindBy(xpath = "//h2[contains(text(),'Bookshelf')]")
//    List<WebElement> bookshelfNames;

//    @FindBy(xpath = "//h2[contains(@class,'XxwSy')]")
//    List<WebElement> bookshelfNames;


    @FindBy(xpath = "//h2[contains(@class,'XxwSy')]")
    List<WebElement> bookshelfNames;


    // Bookshelf prices
    @FindBy(xpath = "//div[@data-testid='plp-product-card']//span[contains(text(),'₹')]")
    List<WebElement> bookshelfPrices;

    @FindBy(xpath = "//div[@aria-label='Sort By filter']")
    WebElement sortBy;

    @FindBy(xpath = "//div[contains(text(),'Discount High to Low')]")
    WebElement discountHighToLow;

    @FindBy(xpath = "//h2[contains(@class,'XxwSy')]")
    List<WebElement> productNames;
//
//    @FindBy(xpath = "//div[@role='link']")
//    List<WebElement> productCards;

    @FindBy(css = "[data-testid='plp-product-card']")
    List<WebElement> productCards;

    @FindBy(xpath = "//*[contains(text(),'Bookshelves')]")
    WebElement bookshelvesCategory;

    @FindBy(xpath = "(//div[@role='link']//img)[1]")
    WebElement firstProductImage;

    @FindBy(xpath = "(//h2[contains(@class,'XxwSy')])[1]")
    WebElement firstProduct;

    @FindBy(css = "span.PpQnM")
    WebElement productsCount;

    public void searchBookshelves() {
        cm.closePopup();
        LoggerManager.info("Waiting for search box");
        cm.closePopup();
        cm.waitForVisibility(searchBox);
        try {
            searchBox.click();
        } catch (ElementClickInterceptedException e) {
            cm.closePopup();
            searchBox.click();
        }
        LoggerManager.info("Clicking search box");
        LoggerManager.info("Typing Bookshelves");
        searchBox.sendKeys("Bookshelves");
        LoggerManager.info("Pressing Enter");
        searchBox.sendKeys(Keys.ENTER);
    }

    public boolean isBookshelvesPageDisplayed() {
        cm.waitForVisibility(title);
        return title.isDisplayed();
    }

    public void openFilters() {
        cm.closePopup();
        LoggerManager.info("Clicking All Filters button");
        cm.jsClick(allFiltersBtn);
        LoggerManager.info("Filters panel opened successfully");
    }

    public void applyPriceFilter() {

        cm.closePopup();

        LoggerManager.info("Clicking All Filters");
        cm.jsClick(allFiltersBtn);

        LoggerManager.info("All Filters opened");

        LoggerManager.info("Scrolling to Price");
        cm.waitForVisibility(priceSection);
        cm.scrollToElement(priceSection);
        priceSection.click();

        LoggerManager.info("Setting max price");
        cm.waitForClickability(maxPriceInput);
        maxPriceInput.clear();

        String maxPrice = ConfigReader.getProperty("maxPrice");

        maxPriceInput.sendKeys(maxPrice);
        LoggerManager.info("Max Price Entered : "
                + maxPriceInput.getAttribute("value"));
        maxPriceInput.sendKeys(Keys.TAB);

        LoggerManager.info("Waiting for UI to update after entering price");

        wait.until(ExpectedConditions.attributeContains(
                maxPriceInput,
                "value",
                maxPrice.substring(0, 2)
        ));

        String oldCount = productsCount.getText();

        LoggerManager.info("Clicking Apply button");
        cm.jsClick(applyFilterBtn);

        LoggerManager.info("Waiting for filtered products to load");

        wait.until(driver ->
                !productsCount.getText().equals(oldCount));

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector("[data-testid='plp-product-card']"),
                0
        ));

        LoggerManager.info("Price filter applied successfully");
    }

    public boolean isPriceFilterApplied() {
        List<Double> prices = getBookshelfPrices();
        int maxPrice = Integer.parseInt(ConfigReader.getProperty("maxPrice"));
        return prices.stream().allMatch(price -> price <= maxPrice);
    }

    public void selectOpenStorage() {
        LoggerManager.info("Scrolling inside All Filters panel");
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector(\"div[role='dialog']\").scrollTop=300");
        LoggerManager.info("Clicking Storage Type");
        WebElement storageType = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//span[text()='Storage Type'])[2]")));
        cm.jsClick(storageType);
        LoggerManager.info("Storage Type expanded");
        LoggerManager.info("Selecting Open Storage");
        WebElement openStorage = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(text(),'Open Storage')]")));
        cm.jsClick(openStorage);
        LoggerManager.info("Open Storage selected successfully");
    }

    public void applyFilters() {
        LoggerManager.info("Clicking Apply Filter button");
        WebElement applyBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(.,'Apply Filter')]")));
        cm.jsClick(applyBtn);
        LoggerManager.info("Filters applied successfully");
        wait.until(ExpectedConditions.invisibilityOf(applyBtn));
    }

    public int getFilteredProductsCount() {
        cm.closePopup();
        WebElement countElement = wait.until(
                ExpectedConditions.visibilityOf(productsCount));
        wait.until(driver -> {
            String text = countElement.getText()
                    .replaceAll("[^0-9]", "");
            if (text.isEmpty()) {
                return false;
            }
            return Integer.parseInt(text) < 607;
        });
        String countText = countElement.getText();
        LoggerManager.info("Count text found: " + countText);
        return Integer.parseInt(
                countText.replaceAll("[^0-9]", "")
        );
    }

    public List<Double> getBookshelfPrices() {

        cm.closePopup();

        List<WebElement> prices = driver.findElements(
                By.xpath("//div[@data-testid='plp-product-card']//span[contains(@class,'sr-only')]")
        );

        LoggerManager.info("Prices Found : " + prices.size());

        List<Double> priceList = new ArrayList<>();

        for (WebElement element : prices) {

            String text = element.getAttribute("innerHTML");

            LoggerManager.info("Price Text = " + text);

            if (text != null && text.startsWith("Deal Price")) {

                String value = text.replace("Deal Price:", "")
                        .replace("₹", "")
                        .replace(",", "")
                        .trim();

                try {
                    priceList.add(Double.parseDouble(value));
                } catch (Exception e) {
                    LoggerManager.info("Skipped : " + text);
                }
            }
        }

        LoggerManager.info("Parsed Prices Count = " + priceList.size());

        return priceList;
    }


    public List<String> getTopThreeBookshelfNames() {

        cm.closePopup();

        List<WebElement> names = driver.findElements(
                By.xpath("//h2[contains(@class,'XxwSy')]")
        );

        LoggerManager.info("Names Found : " + names.size());

        return names.stream()
                .limit(3)
                .map(WebElement::getText)
                .collect(toList());
    }
    public List<String> getTopThreeBookshelfPrices() {

        cm.closePopup();

        List<WebElement> prices = driver.findElements(
                By.xpath("//span[contains(@class,'sr-only') and contains(text(),'Deal Price')]")
        );

        LoggerManager.info("Prices Found : " + prices.size());

        return prices.stream()
                .limit(3)
                .map(e -> e.getAttribute("innerHTML")
                        .replace("Deal Price:", "")
                        .trim())
                .collect(toList());
    }

    public String clickFirstProduct() {
        cm.closePopup();
        cm.waitForVisibility(firstProduct);
        String productName = firstProduct.getText();
        LoggerManager.info("Selected Product : " + productName);
        cm.jsClick(firstProduct);
        return productName;
    }

    public void switchToProductTab() {
        String parent = driver.getWindowHandle();
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(parent)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        LoggerManager.info("Switched to Product tab");
    }

    public void selectPrimaryMaterialEngineeredWood() {
        LoggerManager.info("Expanding Primary Material");
        WebElement primaryMaterial = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='button' and @aria-label='Primary Material']")));
        cm.jsClick(primaryMaterial);
        LoggerManager.info("Selecting Engineered Wood under Primary Material");
        WebElement engineeredWood = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[text()='Engineered Wood'])[1]")));
        cm.jsClick(engineeredWood);
    }

    public void selectTableTopMaterialEngineeredWood() {
        cm.closePopup();
        LoggerManager.info("Scrolling to Table Top Material");
        WebElement tableTopMaterial = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='button' and @aria-label='Table Top Material']")));
        cm.scrollToElement(tableTopMaterial);
        cm.jsClick(tableTopMaterial);
        LoggerManager.info("Selecting Engineered Wood under Table Top Material");
        WebElement engineeredWood = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[text()='Engineered Wood'])[2]")));
        cm.jsClick(engineeredWood);
    }

    public int getProductsCountAfterMaterialFilters() {
        cm.closePopup();
        WebElement countElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h1[contains(text(),'Bookshelves')]/following-sibling::span")
                )
        );
        wait.until(driver -> {
            String countText = countElement.getText().replaceAll("[^0-9]", "");
            return !countText.isEmpty() && Integer.parseInt(countText) < 639;
        });
        String countText = countElement.getText();
        LoggerManager.info("Filtered Products Count : " + countText);
        return Integer.parseInt(countText.replaceAll("[^0-9]", ""));
    }

    public void sortByDiscountHighToLow() {
        cm.closePopup();
        LoggerManager.info("Clicking Sort By");
        cm.click(sortBy);
        LoggerManager.info("Selecting Discount High To Low");
        cm.click(discountHighToLow);
        LoggerManager.info("Discount sorting applied");
        wait.until(ExpectedConditions.visibilityOfAllElements(productCards));
    }

    public void loadTwentyProducts() {
        cm.closePopup();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        while (productNames.size() < 20) {
            int currentCount = productNames.size();
            js.executeScript("window.scrollBy(0,1000)");
            wait.until(driver -> productNames.size() > currentCount || productNames.size() >= 20);
        }
        LoggerManager.info("Minimum 20 products loaded");
    }

    public List<String[]> getTopTwentyProductsWithDiscounts() {
        cm.closePopup();
        wait.until(ExpectedConditions.visibilityOfAllElements(productCards));
        List<String[]> productData = new ArrayList<>();
        int count = Math.min(20, productCards.size());
        for (int i = 0; i < count; i++) {
            WebElement card = productCards.get(i);
            try {
                String name = card.findElement(By.xpath(".//h2[contains(@class,'XxwSy')]")).getText();
                String discount = "N/A";
                List<WebElement> discountElements = card.findElements(By.xpath(".//span[contains(text(),'OFF')]"));
                if (!discountElements.isEmpty()) {
                    discount = discountElements.get(0).getText();
                }
                productData.add(new String[]{name, discount});
            } catch (Exception e) {LoggerManager.error("Unable to extract product details");
            }
        }
        LoggerManager.info("Extracted " + productData.size() + " products with discounts");
        return productData;
    }

    public void clickBookshelvesCategory() {
        cm.closePopup();
        cm.scrollToElement(bookshelvesCategory);
        wait.until(ExpectedConditions.elementToBeClickable(bookshelvesCategory));
        bookshelvesCategory.click();
        LoggerManager.info("Clicked Bookshelves category");
    }

    public String getFirstProductImageUrl() {
        cm.waitForVisibility(firstProductImage);
        return firstProductImage.getAttribute("src");
    }
}