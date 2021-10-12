package Demo.Expleo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserSelection {
	static WebDriver driver;

public static WebDriver usingChrome()
{
System.setProperty("webdriver.chrome.driver", "E:\\SeleniumLibs\\chromedriver.exe");
driver = new ChromeDriver();
driver.manage().window().maximize();
return driver;
}
}