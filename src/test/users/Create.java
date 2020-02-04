package test.users;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main.Base;
import main.ExcelUtils;

public class Create extends Base {
	
	@Test(priority=0, dataProvider="ddt", description="Create a new User")
    public void createUserTest(String username, String email, String firstname, String lastname, String website, String password, String role) throws InterruptedException {
    		
    		// go to new user page
    		btnNewUser();
    		
    		// add username and email
    		addInfos(username, email, firstname, lastname, website, password, role);
    		
    		// save
    		submitUser();
    		
        // check if the user was created
        Thread.sleep(1000);
        String message = getWebDriver().findElement(By.xpath("//*[@id='message']")).getText();
        Assert.assertTrue(message.contains("New user created."));
        
    }
	
	@DataProvider
	public Object[][] ddt(Method method) throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\andre\\eclipse-workspace\\PortfolioTestSelenium\\src\\test\\resources\\ddt\\UsersData.xlsx", "Create", method.getName(), "UC001");
		return (testObjArray);		
	}
	
    public void btnNewUser() throws InterruptedException {
		Actions action = new Actions(getWebDriver());
    		
    		Thread.sleep(1000);
    		action.moveToElement(getWebDriver().findElement(By.xpath("//div[contains(@class, 'wp-menu-name') and normalize-space(text()) = 'Users']"))).perform();
    	
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//li[@id='menu-users']/ul[contains(@class, 'wp-submenu')]/li/a[contains(@href, 'user-new.php')]")).click();
    }
    
    public void btnUsers() throws InterruptedException {
		Thread.sleep(1000);
    		getWebDriver().findElement(By.xpath("//div[contains(@class, 'wp-menu-name') and normalize-space(text()) = 'Users']")).click();
    }
    
    public void addInfos(String username, String email, String firstname, String lastname, String website, String password, String role) throws InterruptedException {
    		Thread.sleep(1000);
    		getWebDriver().findElement(By.xpath("//input[@id='user_login']")).clear();
    		getWebDriver().findElement(By.xpath("//input[@id='user_login']")).sendKeys(username);
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//input[@id='email']")).clear();
    		getWebDriver().findElement(By.xpath("//input[@id='email']")).sendKeys(email);
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//input[@id='first_name']")).clear();
    		getWebDriver().findElement(By.xpath("//input[@id='first_name']")).sendKeys(firstname);
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//input[@id='last_name']")).clear();
    		getWebDriver().findElement(By.xpath("//input[@id='last_name']")).sendKeys(lastname);
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//button[contains(@class, 'button') and normalize-space(text()) = 'Show password']")).click();
    		
    		Thread.sleep(500);
    		WebElement element = getWebDriver().findElement(By.xpath("//input[@id='pass1-text']"));
    		element.clear();
    		for (int i = 0; i < password.length(); i++){
    	        char c = password.charAt(i);
    	        String s = new StringBuilder().append(c).toString();
    	        element.sendKeys(s);
    	    }
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//label[text()[contains(.,'Confirm use of weak password')]]")).click();
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//input[@id='send_user_notification']")).click();
    		
    		Thread.sleep(500);
    		Select select = new Select(getWebDriver().findElement(By.xpath("//select[@id='role']")));
//    		select.deselectAll();
    		select.selectByValue(role); //selectByVisibleText
    		
    }
    
    public void submitUser() throws InterruptedException {
		Thread.sleep(1000);
		getWebDriver().findElement(By.xpath("//input[@id='createusersub']")).click();
    }
    
}
