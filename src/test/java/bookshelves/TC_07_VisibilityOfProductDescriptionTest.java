package bookshelves;

import base.BaseTest;
import org.furniture.pages.BookshelvesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;
import utils.LoggerManager;

public class TC_07_VisibilityOfProductDescriptionTest extends BaseTest {

    @Test
    public void verifyProductDescriptionVisibility() throws InterruptedException {

        LoggerManager.info("Starting TC_07");

        BookshelvesPage page = new BookshelvesPage(driver);

        page.searchBookshelves();

        page.clickFirstProduct();

        page.switchToProductTab();

        Thread.sleep(10000);

        page.clickMoreProductDetails();

        boolean isVisible =
                page.isProductDescriptionVisible();

        Assert.assertTrue(
                isVisible,
                "Product description is not visible");

        LoggerManager.info(
                "Product description section is visible");

        ExtentReportManager.getTest()
                .pass("Product description section is visible");
    }
}