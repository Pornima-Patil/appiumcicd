package pages;

import base.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import util.TestUtil;

/**
 * Created by pornimapatil on 15/01/21, 10:45 AM.
 */
public class LoginPage extends BaseTest {
    TestUtil util = new TestUtil();
    @AndroidFindBy(accessibility = "test-Username")
    @iOSXCUITFindBy(id = "test-Username")
    MobileElement userName;

    @AndroidFindBy(accessibility = "test-Password")
    @iOSXCUITFindBy(id = "test-Password")
    MobileElement password;

    @AndroidFindBy(accessibility = "test-LOGIN")
    @iOSXCUITFindBy(id = "test-LOGIN")
    MobileElement loginBtn;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='test-Error message']/child::XCUIElementTypeStaticText")
    MobileElement errorText;

    public LoginPage enterUserName(String userNameText){
        clear(userName);
        sendKeys(userName, userNameText, "Login with - " + userNameText);
        return this;
    }

    public LoginPage enterPassword(String passwordText){
        clear(password);
        sendKeys(password, passwordText, "Password is " + passwordText);
        return this;
    }
    public ProductsPage clickLoginBtn(){
        click(loginBtn, "Press login button");
        return new ProductsPage();
    }

    public String getErrorText(){
       return getText(errorText, "Error text is: -");

    }

    public ProductsPage login(String userName, String password){
        enterUserName(userName);
        enterPassword(password);
        return clickLoginBtn();
    }






}
