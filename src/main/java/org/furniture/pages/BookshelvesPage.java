package org.furniture.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerManager;
import java.time.Duration;
import java.util.List;

public class BookshelvesPage {

    WebDriver driver;
    WebDriverWait wait;

    public BookshelvesPage(WebDriver driver) {
        this.driver = driver;
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

    // Product Count
    @FindBy(xpath = "//span[contains(.,'Products') and contains(.,'0')]")
    WebElement productsCount;

    // Bookshelf names
    @FindBy(xpath = "//h2[contains(text(),'Bookshelf')]")
    java.util.List<WebElement> bookshelfNames;

    // Bookshelf prices
    @FindBy(xpath ="//div[@role='link']//div[contains(text(),'₹')]")
    java.util.List<WebElement> bookshelfPrices;

    //First Bookshelf
    @FindBy(xpath = "\"(//div[@role='link'])[1]\"")
    WebElement firstBookshelf;

    @FindBy(xpath = "(//div[@role='link'])[1]")
    WebElement firstProductCard;

    //Add to cart Button
    @FindBy(css = "button[data-testid='pdp-add-to-cart-button']")
    WebElement addToCartBtn;

    //Cart Button
    @FindBy(xpath = "//button[contains(text(),'Go to Cart')]")
    WebElement goToCartBtn;

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

    public void openFilters() {
        LoggerManager.info("Clicking All Filters button");
        wait.until(ExpectedConditions.elementToBeClickable(allFiltersBtn));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", allFiltersBtn);
        LoggerManager.info("Filters panel opened successfully");
    }

    public void applyPriceFilter() {
        LoggerManager.info("Clicking All Filters");
        wait.until(ExpectedConditions.elementToBeClickable(allFiltersBtn));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", allFiltersBtn);
        LoggerManager.info("All Filters opened");
        LoggerManager.info("Scrolling to Price");
        wait.until(ExpectedConditions.visibilityOf(priceSection));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", priceSection);
        priceSection.click();
        LoggerManager.info("Setting max price");
        wait.until(ExpectedConditions.elementToBeClickable(maxPriceInput));
        maxPriceInput.clear();
        maxPriceInput.sendKeys("15000");
        maxPriceInput.sendKeys(Keys.TAB);
        LoggerManager.info("Waiting for UI to update after entering price");
        wait.until(ExpectedConditions.attributeContains(
                maxPriceInput,
                "value",
                "15"
        ));
        LoggerManager.info("Clicking Apply button");
        wait.until(ExpectedConditions.elementToBeClickable(applyFilterBtn));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", applyFilterBtn);
        LoggerManager.info("Price filter applied successfully");
    }

    public void selectOpenStorage() {
        LoggerManager.info("Scrolling inside All Filters panel");
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector(\"div[role='dialog']\").scrollTop=300"
        );
        LoggerManager.info("Clicking Storage Type");
        WebElement storageType = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//span[text()='Storage Type'])[2]")
                )
        );
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", storageType);
        LoggerManager.info("Storage Type expanded");
        LoggerManager.info("Selecting Open Storage");
        WebElement openStorage = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(text(),'Open Storage')]")
                )
        );
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", openStorage);
        LoggerManager.info("Open Storage selected successfully");
    }

    public void applyFilters() {
        LoggerManager.info("Clicking Apply Filter button");
        WebElement applyBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='zTzmw undefined']")
                )
        );
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", applyBtn);
        LoggerManager.info("Filters applied successfully");
        wait.until(ExpectedConditions.invisibilityOf(applyBtn));
    }

    public int getFilteredProductsCount() {
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
            return Integer.parseInt(text) < 607;
        });
        String countText = countElement.getText();
        LoggerManager.info("Count text found: " + countText);
        return Integer.parseInt(
                countText.replaceAll("[^0-9]", "")
        );
    }

    public List<Double> getFirstTwentyBookshelfPrices() {

        wait.until(ExpectedConditions.visibilityOfAllElements(bookshelfPrices));

        return bookshelfPrices.stream()
                .filter(WebElement::isDisplayed)
                .limit(20)
                .map(WebElement::getText)
                .map(price -> price.replace("₹", "")
                        .replace(",", "")
                        .trim())
                .map(Double::parseDouble)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<String> getTopThreeBookshelfNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(bookshelfNames));
        return bookshelfNames.stream()
                .limit(3)
                .map(WebElement::getText)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<String> getTopThreeBookshelfPrices() {
        return bookshelfPrices.stream()
                .filter(WebElement::isDisplayed)   // KEY FIX
                .limit(3)
                .map(WebElement::getText)
                .collect(java.util.stream.Collectors.toList());
    }

    public String clickFirstProduct() {

        WebElement product = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//h2[contains(@class,'XxwSy')])[1]")
                )
        );
        String productName = product.getText();
        LoggerManager.info("Selected Product : " + productName);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", product);
        return productName;
    }

    public void switchToProductTab() {

        String parent = driver.getWindowHandle();

        wait.until(driver ->
                driver.getWindowHandles().size() > 1);

        for (String handle : driver.getWindowHandles()) {

            if (!handle.equals(parent)) {

                driver.switchTo().window(handle);
                break;
            }
        }

        LoggerManager.info("Switched to Product tab");
    }

    public void addProductToCart() throws InterruptedException {

        By addToCart = By.cssSelector(
                "button[data-testid='pdp-add-to-cart-button']"
        );

        WebElement button =
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                addToCart));

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView(true)",
                        button);

        Thread.sleep(2000);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        button);

        LoggerManager.info(
                "Clicked Add To Cart");
    }

    public void openCart() {

        By goToCart = By.xpath(
                "//button[contains(text(),'Go to Cart')]");
        wait.until(
                ExpectedConditions.elementToBeClickable(goToCart)
        ).click();
        LoggerManager.info("Cart opened");
    }

    public boolean isProductPresentInCart(String productName) {

        By cartItem = By.xpath("//*[contains(text(),\""
                + productName +
                        "\")]");
        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(cartItem));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickMoreProductDetails() {

        Actions actions = new Actions(driver);

        for (int i = 0; i < 10; i++) {

            actions.sendKeys(Keys.PAGE_DOWN).perform();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            List<WebElement> elements = driver.findElements(
                    By.xpath("//button[contains(.,'More Product Details')]"));

            if (!elements.isEmpty()) {

                elements.get(0).click();

                LoggerManager.info(
                        "Clicked More Product Details");

                return;
            }
        }

        throw new NoSuchElementException(
                "More Product Details not found");
    }

    public boolean isProductDescriptionVisible() {

        try {

            WebElement description = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Product Details')]")
                    )
            );

            return description.isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }
}