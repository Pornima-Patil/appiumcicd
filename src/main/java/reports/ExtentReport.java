package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pornima Patil on 12/02/21, 11:51 AM.
 */
public class ExtentReport {
    static ExtentReports extent;
    final static String filePath = "Extent.html";
    static Map<Integer, ExtentTest> extentTestMap = new HashMap();

    public  static synchronized ExtentReports getReporter(){
        if (extent == null){
            ExtentHtmlReporter html = new ExtentHtmlReporter("Extent.html");
            html.config().setDocumentTitle("Appium FrameWork");
            html.config().setReportName("My App");
            html.config().setTheme(Theme.STANDARD);
            extent = new ExtentReports();
            extent.attachReporter(html);
        }
        return extent;
    }

    public static synchronized ExtentTest getTest(){
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName, String desc){
        ExtentTest test = getReporter().createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }


}
