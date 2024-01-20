import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Test2
{
    RemoteWebDriver driver = null;
    public static String status = "passed";
    public static String username = System.getenv("LT_USERNAME");
    public static String access_key = System.getenv("LT_ACCESS_KEY");

    String testURL = "https://www.lambdatest.com";
    @BeforeMethod
    @Parameters(value={"browser","version","platform", "resolution"})
    public void testSetUp(String browser, String version, String platform, String resolution) throws Exception
    {
        String platformName = System.getenv("HYPEREXECUTE_PLATFORM") != null ? System.getenv("HYPEREXECUTE_PLATFORM") : platform;
        
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("build", "[HyperExecute Lambdatest BootCamp Challenge - 2]");
        capabilities.setCapability("name", "[HyperExecute Lambdatest BootCamp Challenge - 2]");
        capabilities.setCapability("platform", System.getenv("HYPEREXECUTE_PLATFORM"));
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("version", version);

        capabilities.setCapability("tunnel",false);
        capabilities.setCapability("network",true);
        capabilities.setCapability("console",true);
        capabilities.setCapability("visual",true);

        try
        {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), capabilities);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Invalid grid URL");
        }
        System.out.println("Started session");
    }

    @Test(description="Boot Camp Challenge 1")
    public void test1_element_addition_1() throws InterruptedException
    {
        String openingPageURL = ""; 
        String currentPageURL = ""; 

        ExtentReports extent = new ExtentReports("target/surefire-reports/html/extentReport.html");
        ExtentTest test1 = extent.startTest("Boot Camp Challenge 1","Boot Camp Challenge 1");

        driver.get(testURL);
        Thread.sleep(5000);
        test1.log(LogStatus.PASS,"URL is opened");
      //  Thread.sleep(8*60*1000);
        //add 
        /* Selenium Java 3.141.59 */
        WebDriverWait wait = new WebDriverWait(driver, 5);
        /* WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); */
        test1.log(LogStatus.PASS, "Wait created");
        /* Click on the Link */
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS); 
        WebElement seeAllIntrgrationsElement = driver.findElement(By.xpath("//img[@title='See All Integrations']/parent::a[text()='See All Integrations']")); 
        
        openingPageURL = driver.getCurrentUrl(); 
        test1.log(LogStatus.PASS, "Opening Page URL "+ openingPageURL); 
        //WebElement seeAllIntrgrationsElement = driver.findElement(By.xpath("a[text()='See All Integrations']")); 
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", seeAllIntrgrationsElement); 
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seeAllIntrgrationsElement); 

        currentPageURL = driver.getCurrentUrl(); 
        test1.log(LogStatus.PASS, "Current Page URL "+ currentPageURL); 
        String ExpectedTitle = "https://www.lambdatest.com/integrations"; 
        Assert.assertEquals(ExpectedTitle, currentPageURL); 

        status = "passed";
        /* Once you are outside this code, the list would be empty */
    }

    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
        {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}
