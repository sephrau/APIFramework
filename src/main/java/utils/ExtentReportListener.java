package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener  implements ITestListener {
    protected static ExtentReports reports;
    public static ExtentTest test;
    String ReportLocation = "test-result/report/" ;

    public void onTestStart(ITestResult result) {
        test = reports.startTest(result.getMethod().getMethodName());
        test.log(LogStatus.INFO, result.getMethod().getMethodName());
        //System.out.println(result.getTestClass().getTestName());
        //System.out.println(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        test.log(LogStatus.PASS, "Test is pass");
    }

    public void onTestFailure(ITestResult result) {
        test.log(LogStatus.FAIL, "Test is fail");

    }

    public void onTestSkipped(ITestResult result) {
        test.log(LogStatus.SKIP, "Test is skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    public void onStart(ITestContext context) {
        reports = new ExtentReports(ReportLocation + "ExtentReport.html");
    }

    public void onFinish(ITestContext context) {
        reports.endTest(test);
        reports.flush();
    }

}
