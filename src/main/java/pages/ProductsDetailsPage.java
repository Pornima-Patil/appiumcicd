package pages;

import base.BaseTest;
import base.MenuPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * Created by Pornima Patil on 15/01/21, 10:45 AM.
 */
public class ProductsDetailsPage extends MenuPage {
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Description']/android.widget.TextView[1]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='test-Description']/XCUIElementTypeStaticText[1]")
    MobileElement titleSLBT;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Description']/android.widget.TextView[2]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='test-Description']/XCUIElementTypeStaticText[2]")
    private MobileElement textSLB;

    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    @iOSXCUITFindBy(id = "test-BACK TO PRODUCTS")
    MobileElement backToProductsBtn;

    @AndroidFindBy(accessibility = "test-Price")
    @iOSXCUITFindBy(id = "test-Price")
    MobileElement priceSLB;

    @AndroidFindBy(accessibility = "test-Inventory item page")
    AndroidDriver<MobileElement> scrollView;


    @iOSXCUITFindBy(id = "test-ADD TO CART")
    MobileElement addToCartButton;

    public String getSLBTitle() {
        return getText(titleSLBT, "title of SLBT product: -");
    }

    public String getSLBText() {
        return getText(textSLB, "Description of product : -");
    }

    public String getSLBPrice() {
        scrollToElement("test-Price");
        System.out.println("price is -" + priceSLB.getText());
        return getText(priceSLB, "Price of product :-");
    }


    public Boolean verifyAddToCartButton(){
        scrollToElement("test-ADD TO CART");
        System.out.println("Button name :- "+ addToCartButton.getText());
        return addToCartButton.isDisplayed();
    }


    public ProductsPage pressBackToProductsBtn() {
        click(backToProductsBtn, "Back to Products Page button clicked");
        return new ProductsPage();
    }


}
