package pages;

import base.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * Created by pornimapatil on 29/01/21, 11:37 AM.
 */
public class SettingsPage extends BaseTest {
    @AndroidFindBy(accessibility = "test-LOGOUT")
    @iOSXCUITFindBy(id = "test-LOGOUT")
    MobileElement logoutBtn;

    public LoginPage pressLogoutBtn(){
        click(logoutBtn, "Login button is clicked");
        return new LoginPage();

    }
}
