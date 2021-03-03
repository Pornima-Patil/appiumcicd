package pages;

import base.BaseTest;
import base.MenuPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * Created by pornimapatil on 15/01/21, 10:45 AM.
 */
public class ProductsPage extends MenuPage {
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'PRODUCTS')]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='test-Toggle']/parent::*[1]/preceding-sibling::*[1]")
    MobileElement productPageTitle;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='test-Item title'])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name='test-Item title'])[1]")
    MobileElement titleSLBT;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='test-Price'])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name='test-Price'])[1]")
    MobileElement priceSLBT;

    public String getTitle() {
        return getText(productPageTitle, "Title is : -");
    }

    public String getSLBTitle() {
        return getText(titleSLBT, "Title of SLB product: -");
    }

    public String getSLBPrice(){
        return getText(priceSLBT, "Price of SLB product: -");
    }

    public ProductsDetailsPage pressSLBTTitle(){
        click(titleSLBT, "Title of Products Details Page text is - ");
        return new ProductsDetailsPage();
    }


}
