package bookshelves;

import base.BaseTest;
import org.furniture.pages.BookshelvesPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.ExtentReportManager;
import utils.LoggerManager;

public class TC_02_FilterByPriceBelow15000Test extends BaseTest {
    @Test
    public void filterByPriceBelow15000() {
        SoftAssert softAssert = new SoftAssert();

        LoggerManager.info("Starting TC_02 - Price Filter");
        ExtentReportManager.getTest().info("Searching Bookshelves");

        BookshelvesPage page = new BookshelvesPage(driver);

        page.searchBookshelves();
        LoggerManager.info("Bookshelves searched");
        ExtentReportManager.getTest().info("Bookshelves searched");

        page.applyPriceFilter();
        LoggerManager.info("Price filter <= 15000 applied");

        boolean result = page.isPriceFilterApplied();
        LoggerManager.info("Price filter validation result: " + result);
        ExtentReportManager.getTest().info("Validation result: " + result);

        softAssert.assertTrue(result, "Price filter not applied successfully");
        softAssert.assertAll();
    }
}