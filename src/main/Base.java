package main;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Base {

	public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();

//	public static String xlsPath = "/Users/andre/eclipse-workspace/PortfolioTestSelenium/src/test/resources/ddt/";
	public static String xlsPath = "C:\\Users\\andre\\eclipse-workspace\\PortfolioTestSelenium\\src\\test\\resources\\ddt\\";
	
	@BeforeSuite
	public void beforemethod() throws IllegalAccessException, InterruptedException{
		
		new WebDriverFactory();
//		WebDriver driver = WebDriverFactory.create("Firefox");
		WebDriver driver = WebDriverFactory.create("Chrome");
		setWebDriver(driver);
		
//		getWebDriver().manage().window().maximize();
//		getWebDriver().manage().window().setSize(new Dimension(1440, 900));
		getWebDriver().manage().window().fullscreen();
		getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getWebDriver().get("http://www.andreaamado.com/wp-login.php");

		Thread.sleep(500);
		getWebDriver().findElement(By.id("user_login")).clear();
		getWebDriver().findElement(By.id("user_login")).sendKeys("testportfolio");
		getWebDriver().findElement(By.id("user_pass")).clear();
		getWebDriver().findElement(By.id("user_pass")).sendKeys("shd76-dsf3.ewer!djf0)*hhkdlkds56*&b");
		getWebDriver().findElement(By.id("wp-submit")).click();
	}
	
	
	public void setWebDriver(WebDriver driver){
		dr.set(driver);
	}
	
	
	public WebDriver getWebDriver(){
		return dr.get();
	}
	
	@AfterSuite
	public void aftermethod(){
		getWebDriver().quit();
		dr.set(null);
		
		
	}
}
