package base;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import pages.SettingsPage;

/**
 * Created by pornimapatil on 29/01/21, 11:33 AM.
 */
public class MenuPage extends BaseTest{
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Menu']/android.view.ViewGroup/android.widget.ImageView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='test-Menu']/XCUIElementTypeOther")
    MobileElement settingBtn;

    public SettingsPage pressSettingsBtn(){
        click(settingBtn, "Setting button is clicked");
        return new SettingsPage();
    }
}
