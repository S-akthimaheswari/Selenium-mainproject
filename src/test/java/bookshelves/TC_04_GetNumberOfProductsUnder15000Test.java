package bookshelves;

import base.BaseTest;
import org.furniture.pages.BookshelvesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.ExtentReportManager;
import utils.LoggerManager;

import java.util.List;

public class TC_04_GetNumberOfProductsUnder15000Test extends BaseTest {

    @Test
    public void verifyFilteredProductsCount() {

        LoggerManager.info("Starting TC_04");

        BookshelvesPage page = new BookshelvesPage(driver);

        page.searchBookshelves();

        page.applyPriceFilter();

        int productCount = page.getFilteredProductsCount();

        LoggerManager.info(
                "Number of Bookshelves below ₹15000 : " + productCount
        );

        ExtentReportManager.getTest()
                .info("Products below ₹15000 : " + productCount);

        Assert.assertTrue(productCount > 0,
                "No products found after applying filter");

        ExtentReportManager.getTest()
                .pass("Filtered product count = " + productCount);
    }
}