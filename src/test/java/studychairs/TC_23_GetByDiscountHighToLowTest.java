package studychairs;

import base.BaseTest;
import org.furniture.pages.StudyChairsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.LoggerManager;
import utils.PopupHandler;

public class TC_23_GetByDiscountHighToLowTest extends BaseTest {
    @Test
    public void verifyDiscountHighToLow() {
        LoggerManager.info("Starting TC_15");
        StudyChairsPage page = new StudyChairsPage(driver);
        page.searchStudyChairs();
        PopupHandler.closePopupIfPresent(driver);
        page.clickSortBy();
        page.selectDiscountHighToLow();
        page.printTopFiveDiscountProducts();
        Assert.assertEquals(page.getTopFiveProductsCount(), 5, "Top 5 products are not displayed"
        );
    }
}