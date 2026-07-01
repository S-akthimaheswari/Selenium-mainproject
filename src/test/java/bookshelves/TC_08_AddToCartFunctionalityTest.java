package bookshelves;

import base.BaseTest;
import org.furniture.pages.BookshelvesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;
import utils.LoggerManager;

public class TC_08_AddToCartFunctionalityTest extends BaseTest {

    @Test
    public void verifyAddToCartFunctionality() throws InterruptedException {

        LoggerManager.info("Starting TC_08");

        BookshelvesPage page =
                new BookshelvesPage(driver);

        page.searchBookshelves();

        String selectedProduct =
                page.clickFirstProduct();

        LoggerManager.info(
                "Product Selected : " + selectedProduct);

        ExtentReportManager.getTest()
                .info("Selected Product : "
                        + selectedProduct);

        page.switchToProductTab();

        page.addProductToCart();

        ExtentReportManager.getTest()
                .info("Product added to cart");

        page.openCart();

        boolean isPresent =
                page.isProductPresentInCart(
                        selectedProduct);

        Assert.assertTrue(
                isPresent,
                "Selected product not found in cart");

        LoggerManager.info(
                "Verified product present in cart");

        ExtentReportManager.getTest()
                .pass("Product successfully added and verified in cart");
    }
}