package bookshelves;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.furniture.pages.BookshelvesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;
import utils.LoggerManager;

public class TC_06_NavigateToFirstProductTest extends BaseTest {
    @Test
    public void verifyNavigationToFirstProduct() {
        LoggerManager.info("Starting TC_06");
        BookshelvesPage page = new BookshelvesPage(driver);
        page.searchBookshelves();
        ExtentReportManager.getTest().log(Status.INFO, "Searched Bookshelves");
        page.clickFirstProduct();
        page.switchToProductTab();
        ExtentReportManager.getTest().log(Status.INFO, "Switched to First Product Tab");
        Assert.assertEquals(driver.getWindowHandles().size(), 2, "Product page did not open in new tab");
        LoggerManager.info("Successfully navigated to first product");
    }
}