package test.users;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main.Base;
import main.ExcelUtils;

public class Update extends Base {
	
	@Test(priority=0, dataProvider="ddt", description="Update a User")
    public void updateUserTest(String username, String email, String firstname, String lastname, String website, String password, String role) throws InterruptedException {
    		
		// go to users page
    		btnUsers();
    		
    		// select one user that starts with TEST name
    		btnUpdateUser(username);
    		
    		// add username and email
    		addInfos(username, email, firstname, lastname, website, password, role);
    		
    		// save
    		submitUser();
    		
        // check if the user was created
        Thread.sleep(1000);
        String message = getWebDriver().findElement(By.xpath("//*[@id='message']")).getText();
        Assert.assertTrue(message.contains("User updated."));
        
    }
	
	@DataProvider
	public Object[][] ddt(Method method) throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\andre\\eclipse-workspace\\PortfolioTestSelenium\\src\\test\\resources\\ddt\\UsersData.xlsx", "Update", method.getName(), "UC002");
		return (testObjArray);		
	}
	
    public void btnUsers() throws InterruptedException {
		Thread.sleep(1000);
    		getWebDriver().findElement(By.xpath("//div[contains(@class, 'wp-menu-name') and normalize-space(text()) = 'Users']")).click();
    }
    
    public void btnUpdateUser(String username) throws InterruptedException {
		Thread.sleep(1000);
		String path = "(//a[text()[contains(.,'" + username + "')]])[1]";
		getWebDriver().findElement(By.xpath(path)).click();
    }
    
    public void addInfos(String username, String email, String firstname, String lastname, String website, String password, String role) throws InterruptedException {
    		Thread.sleep(1000);
    		getWebDriver().findElement(By.xpath("//input[@id='last_name']")).clear();
    		getWebDriver().findElement(By.xpath("//input[@id='last_name']")).sendKeys(lastname);
    }
    
    public void submitUser() throws InterruptedException {
		Thread.sleep(1000);
		getWebDriver().findElement(By.xpath("//input[@value='Update User' and @id='submit']")).click();
    }
    
}
