package Demo.Expleo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class VerifyPage {
	public WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	
	WebElement btn_blue;
	WebElement btn_red;
	WebElement btn_green;
	ConfigFileReader cf=new ConfigFileReader();
	@BeforeTest
	public void startReport() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/STMExtentReport.html");
		// Create an object of Extent Reports
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "Expleo Interview  Task");
		extent.setSystemInfo("Environment", "Test");
		extent.setSystemInfo("User Name", "Vidya Shekar");
		htmlReporter.config().setDocumentTitle("Title of the Report Comes here ");
		// Name of the report
		htmlReporter.config().setReportName("Name of the Report Comes here ");
		// Dark Theme
		htmlReporter.config().setTheme(Theme.STANDARD);
	}

	// This method is to capture the screenshot and return the path of the
	// screenshot.
	public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@BeforeMethod
	public void setup() {
		driver = BrowserSelection.usingChrome();
		driver.manage().window().maximize();
		driver.get(cf.getApplicationUrl());
	}

	@Test
	public void verifyTitle() {
		logger = extent.createTest("To verify Page Title");
		Assert.assertEquals(driver.getTitle(), "The Internet");
	}

	@Test
	public void verifyBlueButton() {
		logger = extent.createTest("To verify blue button");
		btn_blue=driver.findElement(By.className("button"));
		boolean img = btn_blue.isDisplayed();
		logger.createNode("blue button is Present");
		Assert.assertTrue(img);
		
	}

	@Test
	public void verifyRedButton() {
		logger = extent.createTest("To verify red button");
		btn_red=driver.findElement(By.cssSelector(".button.alert"));
		boolean img = btn_red.isDisplayed();
		logger.createNode("blue button is Present");
		Assert.assertTrue(img);
		
	}
	@Test
	public void verifyGreenButton() {
		logger = extent.createTest("To verify green button");
		btn_green=driver.findElement(By.cssSelector(".button.success")) ;
		boolean img = btn_green.isDisplayed();
		logger.createNode("blue button is Present");
		Assert.assertTrue(img);
		
	}
	
	@Test
	public void verifyGreenColor() {
		logger = extent.createTest("To verify green color of button");
		btn_green=driver.findElement(By.cssSelector(".button.success")) ;
		//boolean img = btn_blue.isDisplayed();
		//logger.createNode("blue button is Present");
		String expValue="button success";
		String actualValue=btn_green.getAttribute("class");
		System.out.println("Value of type attribute: "+actualValue);
		logger.createNode(actualValue + " class is Present");
		Assert.assertEquals(actualValue, expValue);
	}
	
	@Test
	public void verifyRedColor() {
		logger = extent.createTest("To verify red color of button");
		btn_red=driver.findElement(By.cssSelector(".button.alert"));
		String expValue="button alert";
		String actualValue=btn_red.getAttribute("class");
		System.out.println("Value of type attribute: "+actualValue);
		logger.createNode(actualValue + " class is Present");
		Assert.assertEquals(actualValue, expValue);
	}
	
	@Test
	public void verifyblueColor() {
		logger = extent.createTest("To verify blue color of button");
		btn_blue=driver.findElement(By.className("button"));
		String expValue="button";
		String actualValue=btn_blue.getAttribute("class");
		System.out.println("Value of type attribute: "+actualValue);
		logger.createNode(actualValue + " class is Present");
		Assert.assertEquals(actualValue, expValue);
	}
	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			// MarkupHelper is used to display the output in different colors
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			// To capture screenshot path and store the path of the screenshot in the string
			// "screenshotPath"
			// We do pass the path captured by this method in to the extent reports using
			// "logger.addScreenCapture" method.
			// String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
			String screenshotPath = getScreenShot(driver, result.getName());
			// To add it in the extent report
			logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			String screenshotPath = getScreenShot(driver, result.getName());
			logger.pass("Test Case Passed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
			logger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		}
		driver.quit();
	}
	
	@Test
	public void verifyTableColumns() {
		logger = extent.createTest("To verify table columns");
		//Finding number of Columns
		List<WebElement> columnsNumber = driver.findElements(By.xpath("//tbody//tr[1]/td"));
		int columnCount = columnsNumber.size();
		System.out.println("No of columns in this table : " + columnCount);
		
		logger.createNode(columnCount + " is the number of columns");
		Assert.assertEquals(columnCount, 7);	
		
	}
	
	@Test
	public void verifyTableRows() {
		logger = extent.createTest("To verify table rows");
		//Finding number of Columns
		List<WebElement> rowNumber = driver.findElements(By.xpath("//tbody/tr"));
		int rowCount = rowNumber.size();
		System.out.println("No of rows in this table : " + rowCount);
		logger.createNode(rowCount + " is the number of columns");
		Assert.assertEquals(rowCount, 10);	
		
	}
	
	
	@Test
	public void verifyTabledata() {
		logger = extent.createTest("To verify table data");
		//Finding cell value at 4th row and 4th column
		WebElement cellAddress = driver.findElement(By.xpath("//tbody/tr[4]/td[4]"));
		String value = cellAddress.getText();
		System.out.println("The Cell Value is : "+value);
		logger.createNode(value + " is the cell value at 4,4");
		Assert.assertEquals(value, "Definiebas3");	
		
	}
	@AfterTest
	public void endReport() {
		extent.flush();
	}
}
