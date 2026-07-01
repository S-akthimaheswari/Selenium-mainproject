package org.furniture.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import utils.LoggerManager;
import java.time.Duration;
import java.util.Set;

public class GiftCardsPage {

    WebDriver driver;
    WebDriverWait wait;
    public GiftCardsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    // Gift Cards link
    @FindBy(xpath = "//a[text()='Gift Cards']")
    WebElement giftCardsLink;
    // Anniversary card
    By anniversaryCard = By.xpath("(//*[@id='design-theme']//img)[3]");
    public void clickGiftCards() {
        LoggerManager.info("Clicking Gift Cards link");
        wait.until(ExpectedConditions.elementToBeClickable(giftCardsLink));
        giftCardsLink.click();
    }

    public void switchToGiftCardWindow() {
        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
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

    public void selectAnniversaryCard() {
        WebElement card = wait.until(
                ExpectedConditions.visibilityOfElementLocated(anniversaryCard)
        );
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
                card
        );
        wait.until(ExpectedConditions.elementToBeClickable(card));
        wait.until(driver -> card.isDisplayed() && card.isEnabled());
        js.executeScript("arguments[0].click();", card);
        wait.until(ExpectedConditions.attributeContains(card, "class", "border-secondary"));
        LoggerManager.info("Anniversary card selected");
    }

    public boolean isAnniversaryCardSelected() {
        WebElement card = driver.findElement(anniversaryCard);
        return card.getAttribute("class").contains("border-secondary");
    }

}