package tests;

import base.BaseTest;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.ProductsDetailsPage;
import pages.ProductsPage;
import pages.SettingsPage;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pornimapatil on 15/01/21, 12:00 PM.
 */
public class ProductsPageTest extends BaseTest {

    LoginPage loginPage;
    ProductsPage productsPage;
    SettingsPage settingsPage;
    ProductsDetailsPage productsDetailsPage;
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
        productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("userName"),
                loginUsers.getJSONObject("validUser").getString("password"));
    }

    @AfterMethod
    public void afterMethod(){
        settingsPage = productsPage.pressSettingsBtn();
        loginPage = settingsPage.pressLogoutBtn();
    }

    @Test
    public void validateAProductOnProductsPage() {
        SoftAssert sa = new SoftAssert();
        String SLBTitle = productsPage.getSLBTitle();
        sa.assertEquals(SLBTitle, strings.get("products_page_slb_title"));

        String SLBPrice = productsPage.getSLBPrice();
        sa.assertEquals(SLBPrice, strings.get("products_page_slb_price"));
        sa.assertAll();
    }

    @Test
    public void validateProductOnProductDetailsPage() {
        SoftAssert sa = new SoftAssert();
        productsDetailsPage = productsPage.pressSLBTTitle();

        String SLBTitle = productsDetailsPage.getSLBTitle();
        sa.assertEquals(SLBTitle, strings.get("products_page_slb_title"));

        String SLBText = productsDetailsPage.getSLBText();
        sa.assertEquals(SLBText, strings.get("products_details_page_slb_text"));

        String SLBPrice = productsDetailsPage.getSLBPrice();
        sa.assertEquals(SLBPrice, strings.get("products_details_page_slb_price"));

       // sa.assertTrue(productsDetailsPage.verifyAddToCartButton());

        productsPage = productsDetailsPage.pressBackToProductsBtn();
        sa.assertAll();
    }
}