package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportUtils {

    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initializeReport() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void logPass(String message) {
        test.log(Status.PASS, message);
    }

    public static void logFail(String message) {
        test.log(Status.FAIL, message);
    }

    public static void flushReport() {
        extent.flush();
    }
}