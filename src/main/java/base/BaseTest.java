package base;

import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reports.ExtentReport;
import util.TestUtil;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by pornimapatil on 14/01/21, 12:11 PM.
 */
public class BaseTest {
    protected static AppiumDriver driver;
    protected static Properties prop;
    protected static HashMap<String, String> strings = new HashMap<String, String>();
    protected static String dateTime;
    protected static String platform;
    InputStream inputStream;
    InputStream stringsis;
    private static AppiumDriverLocalService server;
    TestUtil util = new TestUtil();

    public BaseTest() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Super Before Method");
        ((CanRecordScreen) driver).startRecordingScreen();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("Super After Method");
        String media = ((CanRecordScreen) driver).stopRecordingScreen();
        // As 2 is int value for failure status it is used to check failure cases
        if (result.getStatus() == 2) {
            Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
            String dir = "videos" + File.separator + params.get("platformName") + "_" + params.get("platformVersion")
                    + "_" + params.get("deviceName") + File.separator + dateTime + File.separator + result.getTestClass().getRealClass().getSimpleName();

            File videoDir = new File(dir);
            if (!videoDir.exists()) {
                videoDir.mkdirs(); // create multiple directories
            }

            try {
                FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
                stream.write(Base64.decodeBase64(media));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        server = getAppiumServerDefault();
        if (!server.isRunning()) {
            server.start();
            server.clearOutPutStreams();
            System.out.println("Server is started.");
        } else {
            System.out.println("Server is already running.");
        }
    }

    @AfterSuite
    public void afterSuite() {
        server.stop();
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        //return AppiumDriverLocalService.buildDefaultService();
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withLogFile(new File("serverLogs/server.log")));
    }

    @Parameters({"emulator", "platformName", "platformVersion", "udid", "deviceName"})
    @BeforeTest
    public void beforeTest(String emulator, String platformName, String platformVersion, String udid, String deviceName) throws IOException {
        platform = platformName;
        URL url;
        dateTime = util.getDateTime();

        try {
            prop = new Properties();
            String propFileName = "config.properties";
            String xmlFileName = "strings/strings.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            prop.load(inputStream);

            stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            strings = util.parseStringXML(stringsis);

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", platformName);
            desiredCapabilities.setCapability("deviceName", deviceName);

            switch (platformName) {
                case "Android":
                    desiredCapabilities.setCapability("automationName", prop.getProperty("androidAutomationName"));
                    desiredCapabilities.setCapability("appPackage", prop.getProperty("androidAppPackage"));
                    desiredCapabilities.setCapability("appActivity", prop.getProperty("androidAppActivity"));
                    if (emulator.equalsIgnoreCase("true")) {
                        desiredCapabilities.setCapability("platformVersion", platformVersion);
                        desiredCapabilities.setCapability("avd", deviceName);
                    } else {
                        desiredCapabilities.setCapability("udid", udid);
                    }
                    String androidAppUrl = getClass().getResource(prop.getProperty("androidAppLocation")).getFile();
                    desiredCapabilities.setCapability(MobileCapabilityType.APP, androidAppUrl);

                    url = new URL(prop.getProperty("appiumURL"));
                    driver = new AndroidDriver(url, desiredCapabilities);
                    break;

                case "iOS":
                    desiredCapabilities.setCapability("automationName", prop.getProperty("iOSAutomationName"));
                    desiredCapabilities.setCapability("platformVersion", platformVersion);
                    desiredCapabilities.setCapability("bundleId", prop.getProperty("iOSBundleId"));
                    String iOSAppUrl = getClass().getResource(prop.getProperty("iOSAppLocation")).getFile();
                    //desiredCapabilities.setCapability(MobileCapabilityType.APP, iOSAppUrl);

                    url = new URL(prop.getProperty("appiumURL"));
                    driver = new IOSDriver(url, desiredCapabilities);
                    break;

                default:
                    throw new Exception("Invalid platform: - " + platformName);
            }
//            String sessionId = driver.getSessionId().toString();
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (stringsis != null) {
                stringsis.close();
            }
        }
    }


    public AppiumDriver getDriver() {
        return driver;
    }

    // Method for wait until visibility of element
    public void waitForVisibilityOfElement(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(driver, TestUtil.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPlatform(){
        return platform;
    }

    public void click(MobileElement element, String msg) {
        waitForVisibilityOfElement(element);
        element.click();
        util.log().info(msg);
        ExtentReport.getTest().log(Status.INFO, msg);
    }

    public void clear(MobileElement element) {
        waitForVisibilityOfElement(element);
        element.clear();
    }

    public void sendKeys(MobileElement element, String text, String msg) {
        waitForVisibilityOfElement(element);
        element.sendKeys(text);
        util.log().info(msg);
        ExtentReport.getTest().log(Status.INFO, msg);
    }

    public String getAttribute(MobileElement element, String attribute) {
        waitForVisibilityOfElement(element);
        return element.getAttribute(attribute);
    }

    public String getText(MobileElement element, String msg) {
        String text = null;
        switch (platform) {
            case "Android":
                text = getAttribute(element, "text");
                break;

            case "iOS":
                text = getAttribute(element, "label");
                break;
        }
        util.log().info(msg + text);
        ExtentReport.getTest().log(Status.INFO, msg + text);
        return text;
    }

    public void closeApp() {
        driver.closeApp();
    }

    public void launchApp() {
        driver.launchApp();
    }

    //Scroll to element for Android and iOS
    public void scrollToElement(String text) {
        switch (platform) {
            case "Android":
                MobileElement element = (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
                        "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                                + "new UiSelector().description(\"" + text + "\"));");
                break;

            case "iOS":
                RemoteWebElement element1 = (RemoteWebElement) driver.findElement(By.name(text));
                String elementID = element1.getId();
                HashMap<String, String> scrollObject = new HashMap<String, String>();
                scrollObject.put("element", elementID);
                scrollObject.put("toVisible", "any non empty string");
                driver.executeScript("mobile:scroll", scrollObject);
                break;
        }
    }


//    // Scroll to the Element
//    public void scrollToElement(String text) {
//        MobileElement element = (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
//                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
//                        + "new UiSelector().description(\"" + text + "\"));");
//    }
//
//        public void iOSScrollToElement (String text){
//            //scroll to specific element
////        RemoteWebElement parent = (RemoteWebElement) driver.findElement(By.className("XCUIElementTypeScrollView"));
////        String parentID = parent.getId();
////        HashMap<String, String> scrollObject = new HashMap<String, String>();
////        scrollObject.put("element", parentID);
////        scrollObject.put("name", text);
////        driver.executeScript("mobile:scroll", scrollObject);
//
//            //scroll to specific element id accessibility ID
//            RemoteWebElement element = (RemoteWebElement) driver.findElement(By.name(text));
//            String elementID = element.getId();
//            HashMap<String, String> scrollObject = new HashMap<String, String>();
//            scrollObject.put("element", elementID);
//            scrollObject.put("toVisible", "any non empty string");
//            driver.executeScript("mobile:scroll", scrollObject);
//        }


    @AfterTest
    public void afterTest() {
        driver.quit();
    }


}
