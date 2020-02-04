package test.posts;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main.Base;
import main.ExcelUtils;

public class Delete extends Base {
	
	@Test(priority=2, dataProvider="ddt", description="Delete a Post")
    public void deletePostTest(String title, String content, String media) throws InterruptedException {
    		
    		// go to posts page
    		btnPosts();
    		
    		// get the first post of the list
    		btnDeletePost(title);
    		
        // check if the post was created
        Thread.sleep(1000);
        String message = getWebDriver().findElement(By.xpath("//*[@id='message']")).getText();
        Assert.assertTrue(message.contains("1 post moved to the Trash."));
    }
	
	@DataProvider
	public Object[][] ddt(Method method) throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(xlsPath + "PostsData.xlsx", "Delete", method.getName(), "UC045");
		return (testObjArray);		
	}
    
    public void btnPosts() throws InterruptedException {
		Thread.sleep(1000);
    		getWebDriver().findElement(By.xpath("//div[contains(@class, 'wp-menu-name') and normalize-space(text()) = 'Posts']")).click();
    }
    
    public void btnDeletePost(String title) throws InterruptedException {
    		Actions action = new Actions(getWebDriver());
    		
    		String pathTitle = "(//tbody[@id='the-list']//strong//a[starts-with(text(),'" + title + "')])[1]";
    		//String pathTitle = "(//tbody[@id='the-list']//strong//a[contains(text(), '" + title + "')])[1]"; //[starts-with(text(),'" + title + "')]
        	
    		String pathBtn = "(//tbody[@id='the-list']//strong//a[starts-with(text(),'" + title + "')]/following::div[@class='row-actions']/span[@class='trash']/a[@class='submitdelete'])[1]";
    		
    		Thread.sleep(1000);
    		action.moveToElement(getWebDriver().findElement(By.xpath(pathTitle))).perform();
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath(pathBtn)).click();
    }
    
    
}
