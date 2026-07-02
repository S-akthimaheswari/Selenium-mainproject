package org.furniture.pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import utils.LoggerManager;
import utils.PopupHandler;

import java.time.Duration;

public class GiftCardsPage {
    WebDriver driver;
    WebDriverWait wait;
    public GiftCardsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }
    // Gift Cards Link
    @FindBy(xpath = "//a[text()='Gift Cards']")
    WebElement giftCardsLink;
    // Anniversary Card
    By anniversaryCard = By.xpath("(//*[@id='design-theme']//img)[3]");
    // Form Fields
    By amountField = By.id("denomination");
    By quantityField = By.id("quantity");
    // Sender Details
    public By senderFirstName =
            By.xpath("//div[@id='sender-details']//input[@id='firstname']");
    By senderLastName =
            By.xpath("//div[@id='sender-details']//input[@id='lastname']");
    By senderEmail =
            By.xpath("//div[@id='sender-details']//input[@id='email']");
    By senderMobile =
            By.xpath("//div[@id='sender-details']//input[@id='telephone']");
    // Receiver Details
    By receiverFirstName =
            By.xpath("//div[@id='receiver-details']//input[@id='firstname']");
    By receiverLastName =
            By.xpath("//div[@id='receiver-details']//input[@id='lastname']");
    By receiverEmail =
            By.xpath("//div[@id='receiver-details']//input[@id='email']");
    // Message
    By messageBox = By.id("giftMessage");
    // Email Validation Message
    By senderEmailError =
            By.xpath("//div[contains(@class,'invalid-address')]");

// Navigation

    public void clickGiftCards() {
        PopupHandler.closePopupIfPresent(driver);
        LoggerManager.info("Clicking Gift Cards link");
        wait.until(ExpectedConditions.elementToBeClickable(giftCardsLink)).click();
    }

    public void switchToGiftCardWindow() {
        PopupHandler.closePopupIfPresent(driver);
        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        wait.until(ExpectedConditions.urlContains("woohoo"));
        LoggerManager.info("Switched to Gift Card window");
    }

    public boolean isGiftCardPageOpened() {
        return driver.getCurrentUrl().contains("woohoo");
    }

    // Anniversary Card
    public void selectAnniversaryCard() {
        WebElement card = wait.until(
                ExpectedConditions.visibilityOfElementLocated(anniversaryCard)
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                card
        );
        wait.until(ExpectedConditions.elementToBeClickable(card));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", card
        );
        LoggerManager.info("Anniversary card selected");
    }

    public boolean isAnniversaryCardSelected() {

        try {
            WebElement card = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(anniversaryCard)
            );
            return card.getAttribute("class")
                    .contains("border-secondary");
        } catch (Exception e) {
            return false;
        }
    }
    // Form
    public void waitForGiftCardFormToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(amountField));
        LoggerManager.info("Gift card form loaded");
    }

    public void fillGiftCardForm(String amount,
                                 String quantity,
                                 String senderFName,
                                 String senderLName,
                                 String senderEmailVal,
                                 String senderMobileVal,
                                 String receiverFName,
                                 String receiverLName,
                                 String receiverEmailVal,
                                 String message) {

        PopupHandler.closePopupIfPresent(driver);
        LoggerManager.info("Filling Gift Card form");

        WebElement amt = wait.until(
                ExpectedConditions.elementToBeClickable(amountField));
        amt.clear();
        amt.sendKeys(amount);

        WebElement qty = wait.until(
                ExpectedConditions.elementToBeClickable(quantityField));
        qty.clear();
        qty.sendKeys(quantity);

        WebElement sFN = wait.until(
                ExpectedConditions.elementToBeClickable(senderFirstName));
        sFN.clear();
        sFN.sendKeys(senderFName);

        WebElement sLN = wait.until(
                ExpectedConditions.elementToBeClickable(senderLastName));
        sLN.clear();
        sLN.sendKeys(senderLName);

        WebElement sEmail = wait.until(
                ExpectedConditions.elementToBeClickable(senderEmail));
        sEmail.clear();
        sEmail.sendKeys(senderEmailVal);

        WebElement sMob = wait.until(
                ExpectedConditions.elementToBeClickable(senderMobile));
        sMob.clear();
        sMob.sendKeys(senderMobileVal);

        WebElement rFN = wait.until(
                ExpectedConditions.elementToBeClickable(receiverFirstName));
        rFN.clear();
        rFN.sendKeys(receiverFName);

        WebElement rLN = wait.until(
                ExpectedConditions.elementToBeClickable(receiverLastName));
        rLN.clear();
        rLN.sendKeys(receiverLName);

        WebElement rEmail = wait.until(
                ExpectedConditions.elementToBeClickable(receiverEmail));
        rEmail.clear();
        rEmail.sendKeys(receiverEmailVal);

        WebElement msg = wait.until(
                ExpectedConditions.elementToBeClickable(messageBox));
        msg.clear();
        msg.sendKeys(message);

        wait.until(ExpectedConditions.attributeToBe(
                senderFirstName, "value", senderFName));

        wait.until(ExpectedConditions.attributeToBe(
                senderEmail, "value", senderEmailVal));

        LoggerManager.info("Gift Card form filled successfully");
    }
    // Email Validation
    public void triggerEmailValidation() {
        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(senderEmail)
        );
        emailField.sendKeys(Keys.TAB);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(senderEmailError)
        );
        LoggerManager.info("Email validation triggered");
    }

    public String getSenderEmailErrorMessage() {
        WebElement error = wait.until(
                ExpectedConditions.visibilityOfElementLocated(senderEmailError)
        );
        return error.getText().trim();
    }
    // Getters
    public String getAmountValue() {
        return driver.findElement(amountField).getAttribute("value");
    }

    public String getQuantityValue() {
        return driver.findElement(quantityField).getAttribute("value");
    }

    public String getSenderFirstNameValue() {
        return driver.findElement(senderFirstName).getAttribute("value");
    }

    public String getSenderLastNameValue() {
        return driver.findElement(senderLastName).getAttribute("value");
    }

    public String getSenderEmailValue() {
        return driver.findElement(senderEmail).getAttribute("value");
    }

    public String getSenderMobileValue() {
        return driver.findElement(senderMobile).getAttribute("value");
    }

    public String getReceiverFirstNameValue() {
        return driver.findElement(receiverFirstName).getAttribute("value");
    }

    public String getReceiverLastNameValue() {
        return driver.findElement(receiverLastName).getAttribute("value");
    }

    public String getReceiverEmailValue() {
        return driver.findElement(receiverEmail).getAttribute("value");
    }
}