package test.users;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main.Base;
import main.ExcelUtils;

public class Delete extends Base {
	
	@Test(priority=0, dataProvider="ddt", description="Delete a User")
    public void deleteUserTest(String username, String email, String firstname, String lastname, String website, String password, String role) throws InterruptedException {
    		
    		// go to users page
    		btnUsers();
    		
    		// delete one user that starts with TEST name
    		btnDeleteUser(username);
    		
    		// confirm
    		btnConfirm();
    		
        // check if the user was created
        Thread.sleep(1000);
        String message = getWebDriver().findElement(By.xpath("//*[@id='message']")).getText();
        Assert.assertTrue(message.contains("User deleted."));
        
    }
	
	@DataProvider
	public Object[][] ddt(Method method) throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\andre\\eclipse-workspace\\PortfolioTestSelenium\\src\\test\\resources\\ddt\\UsersData.xlsx", "Delete", method.getName(), "UC003");
		return (testObjArray);		
	}
	
    public void btnUsers() throws InterruptedException {
		Thread.sleep(1000);
    		getWebDriver().findElement(By.xpath("//div[contains(@class, 'wp-menu-name') and normalize-space(text()) = 'Users']")).click();
    }
    
    public void btnDeleteUser(String username) throws InterruptedException {
		Actions action = new Actions(getWebDriver());
		
		String pathTitle = "(//a[text()[contains(.,'" + username + "')]])[1]";
	
		String pathBtn = "(//a[text()[contains(.,'" + username + "')]]/following::div[@class='row-actions']/span[@class='delete']/a[@class='submitdelete'])[1]";
		
		Thread.sleep(1000);
		action.moveToElement(getWebDriver().findElement(By.xpath(pathTitle))).perform();
		
		Thread.sleep(500);
		getWebDriver().findElement(By.xpath(pathBtn)).click();
    }
    
    public void btnConfirm() throws InterruptedException {
    		Thread.sleep(1000);
		getWebDriver().findElement(By.xpath("//input[@value='Confirm Deletion']")).click();
    }
    
}
