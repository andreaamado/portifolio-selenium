package test.login;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main.ExcelUtils;
import main.WebDriverFactory;

public class Login extends WebDriverFactory {
	
	public ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();
	
	WebDriver driver;
	
	String message;
	
	@Test(priority=0, dataProvider="ddt", description="Login into the system")
    public void loginTest(String username, String password, String remember, String lost) throws InterruptedException, IllegalAccessException {
		new WebDriverFactory();
//		driver = WebDriverFactory.create("Firefox");
		driver = WebDriverFactory.create("Chrome");
		
//		driver.manage().window().maximize();
//		driver.manage().window().setSize(new Dimension(1440, 900));
		driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://www.andreaamado.com/wp-login.php");
		
		Thread.sleep(500);
		driver.findElement(By.id("user_login")).clear();
		driver.findElement(By.id("user_login")).sendKeys(username);
		driver.findElement(By.id("user_pass")).clear();
		driver.findElement(By.id("user_pass")).sendKeys(password);
		driver.findElement(By.id("wp-submit")).click();
        
		Thread.sleep(500);
        message = driver.findElement(By.xpath("//*[@id='login_error']")).getText();
		
		Thread.sleep(1500);
		driver.quit();
		dr.set(null);
        
        if (message.contains("The password you entered for the username")) {
        		Assert.assertTrue(message.contains("The password you entered for the username"));
        } else if (message.contains("The password field is empty")) {
        		Assert.assertTrue(message.contains("The password field is empty"));
        } else if (message.contains("The username field is empty")) {
        		Assert.assertTrue(message.contains("The username field is empty"));
        } else if (message.contains("Invalid username")) {
        		Assert.assertTrue(message.contains("Invalid username"));
        }
    }
	
	@DataProvider
	public Object[][] ddt(Method method) throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\andre\\eclipse-workspace\\PortfolioTestSelenium\\src\\test\\resources\\ddt\\LoginData.xlsx", "Login", method.getName(), "UC009");
		return (testObjArray);		
	}

}
