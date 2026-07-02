package org.furniture.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerManager;
import utils.PopupHandler;

public class StudyChairsPage {

    WebDriver driver;
    WebDriverWait wait;

    public StudyChairsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Search Box
    @FindBy(id = "searchInput")
    WebElement searchBox;
    // Search Result Page Heading
    @FindBy(xpath = "//h1[contains(text(),'Study Chairs')]")
    WebElement title;

    // Search for Study Chairs
    public void searchStudyChairs() {
        PopupHandler.closePopupIfPresent(driver);
        LoggerManager.info("Waiting for search box");
        wait.until(ExpectedConditions.visibilityOf(searchBox));
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

    // Verify page is displayed
    public boolean isStudyChairsPageDisplayed() {
        return !driver.findElements(
                        By.xpath("//h1[contains(text(),'Study Chairs')]"))
                .isEmpty();
    }
}