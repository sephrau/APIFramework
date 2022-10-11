package test;


import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.ConfigReader;
import utils.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class InitTest extends ExtentReportListener{

    @BeforeClass
    public void baseTest() {
        RestAssured.baseURI = ConfigReader.getConfigReader().get("baseUrl");
    }
}
