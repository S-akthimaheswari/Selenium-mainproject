package bookshelves;

import base.BaseTest;
import org.furniture.pages.BookshelvesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExcelUtils;
import utils.ExtentReportManager;
import utils.LoggerManager;

import java.util.List;

public class TC_08_SortByDiscountHighToLowAndExtractTop20ProductsTest extends BaseTest {

    @Test
    public void sortByDiscountAndExtractTop20Products() {

        LoggerManager.info("Starting TC_08 - Discount High To Low Extraction");
        BookshelvesPage page = new BookshelvesPage(driver);
        ExtentReportManager.getTest().info("Searching Bookshelves");
        page.searchBookshelves();
        ExtentReportManager.getTest().info("Sorting by Discount High To Low");
        page.sortByDiscountHighToLow();
        page.loadTwentyProducts();
        List<String[]> productData = page.getTopTwentyProductsWithDiscounts();
        LoggerManager.info("Total products extracted : " + productData.size());
        Assert.assertNotNull(productData, "Product data list is null");
        Assert.assertFalse(productData.isEmpty(), "No products were extracted");
        Assert.assertTrue(productData.size() >= 20,
                "Less than 20 products were extracted. Actual count: " + productData.size());
        ExcelUtils.writeDiscountData(productData);
        LoggerManager.info("Successfully extracted and stored top 20 discounted products");
    }
}