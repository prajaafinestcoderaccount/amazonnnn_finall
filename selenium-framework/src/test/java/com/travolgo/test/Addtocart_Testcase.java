package com.travolgo.test;

import java.time.Duration;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.travolgo.pages.CheckoutPOM;
import com.travolgo.pages.LoginPOM;
import com.travolgo.pages.SearchPOM;

public class Addtocart_Testcase {

	WebDriver driver;
	WebDriverWait wait;
	ExtentReports extent;
	ExtentTest test;

	private static Logger logger = LogManager.getLogger(Addtocart_Testcase.class);

	@BeforeSuite
	public void setupReport() {
		String reportPath = System.getProperty("user.dir") + "/reports/Amazon_AddToCart_Report.html";
		ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

		spark.config().setDocumentTitle("Amazon Automation Test Report");
		spark.config().setReportName("Add To Cart Test Execution Report");
		spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(spark);
		logger.info("Extent report initialized");
	}

	@BeforeTest
	void setup() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/?&tag=googhydrabk1-21&ref=pd_sl_5km84u9k2k_e&adgrpid=155259813113&hvpone=&hvptwo=&hvadid=674842289479&hvpos=&hvnetw=g&hvrand=5565983177309888141&hvqmt=e&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9062046&hvtargid=kwd-304880464215&hydadcr=14450_2316420&gad_source=1");
//      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//		WebElement website = driver.findElement(By.id("APjFqb"));
//		website.sendKeys("amazon.in",Keys.ENTER);
//		WebElement open = driver.findElement(By.xpath("//div[@class='GUyUUb']//div//div[@class='uEierd']//div//div//span[contains(text(),'Shop online at Amazon India')]"));
//		open.click();
//		logger.info("opened amazon");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void Addingtocart() {
		test = extent.createTest("Add to Cart Test");

		try {
			// LOGIN
			WebElement loginModule = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Account & Lists']")));
			loginModule.click();

			LoginPOM ls = new LoginPOM(driver);
			ls.Email_value("archana1575@gmail.com");
			ls.Click_continue_button();
			ls.Password_value("hariprajaa13");
			ls.Click_signin_button();

			test.pass("Logged in successfully");

			// SEARCH
			SearchPOM search = new SearchPOM(driver);
			search.search_field("pencil box for kids");
			test.pass("Search successful");

			// ADD TO CART
			WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("a-autoid-7-announce")));
			addButton.click();

			// VERIFY CART COUNT
			WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-cart-count")));
			int count = Integer.parseInt(cartCount.getText());
			Assert.assertTrue(count > 0);
			test.pass("Item added to cart");

			// checkout
			CheckoutPOM checkout = new CheckoutPOM(driver);

			checkout.openCart();
			test.pass("opened cart");
			checkout.proceedToBuy();
			test.pass("proceeded to buy");
			checkout.continueAddress();
			test.pass("clicked continue");
			Thread.sleep(2000);
			checkout.selectUPIAndVerify("hariprajaa05-1@okhdfcbank");
			test.pass("upi id entered and verified");
			checkout.continuePayment();
			test.pass("payment request sent to user ");

		} catch (Exception e) {
			test.fail("Test failed: " + e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			// driver.quit();
		}
		extent.flush();
		logger.info("Report generated");
	}
}