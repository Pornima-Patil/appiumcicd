package tests;

import base.BaseTest;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import reports.ExtentReport;
import util.TestUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pornimapatil on 15/01/21, 12:00 PM.
 */
public class LoginPageTest extends BaseTest {
    TestUtil util = new TestUtil();
    LoginPage loginPage;
    ProductsPage productsPage;
    InputStream details;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws IOException {
        try {
            String dataFileName = "data/loginUser.json";
            details = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(details);
            loginUsers = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (details != null) {
                details.close();
            }
        }
        closeApp();
        launchApp();
    }

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage();
        util.log().info("\n" + "****** Starting Test ******" + "\n");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("LoginTest After Method");
    }

    @Test
    public void invalidUserName() {
     //  ExtentReport.startTest("invalidUserName", "This is description");

        loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("userName"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
        loginPage.clickLoginBtn();
        loginPage.getErrorText();

        String actualErrorText = loginPage.getErrorText();
        String expectedErrorText = strings.get("error_invalid_username_or_password");
        System.out.println("actual error text:" + actualErrorText + "\n" + "expected error text" + expectedErrorText);
        Assert.assertEquals(actualErrorText, expectedErrorText);
    }

    @Test
    public void invalidPassword() {
        loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("userName"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
        loginPage.clickLoginBtn();
        loginPage.getErrorText();

        String actualErrorText = loginPage.getErrorText();
        String expectedErrorText = strings.get("error_invalid_username_or_password");
        System.out.println("actual error text:" + actualErrorText + "\n" + "expected error text" + expectedErrorText);
        Assert.assertEquals(actualErrorText, expectedErrorText);
    }

    @Test
    public void successfulLogin() {
        loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("userName"));
        loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
        productsPage = loginPage.clickLoginBtn();

        String actualProductTitle = productsPage.getTitle();
        String expectedProductTitle = strings.get("product_title");
        System.out.println("actual error text:" + actualProductTitle + "\n" + "expected error text" + expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }

}

