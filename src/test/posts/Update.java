package test.posts;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main.Base;
import main.ExcelUtils;

public class Update extends Base {
	
	@Test(priority=0, dataProvider="ddt", description="Update a Post")
    public void updatePostTest(String title, String content, String media) throws InterruptedException {
    		
    		// go to posts page
    		btnPosts();
    		
    		// get the first post of the list
    		btnPostTitle(title);
    		
    		// add title
    		addTitle(title);
    		
    		// add content
    		addContent(content);
    		
    		// save
    		submitPost();
    		
        // check if the post was created
        Thread.sleep(1000);
        String message = getWebDriver().findElement(By.xpath("//*[@id='message']")).getText();
        Assert.assertTrue(message.contains("Post updated."));
        
    }
	
	@DataProvider
	public Object[][] ddt(Method method) throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(xlsPath + "PostsData.xlsx", "Update", method.getName(), "UC045");
		return (testObjArray);		
	}
    
    public void btnPosts() throws InterruptedException {
		Thread.sleep(1000);
		getWebDriver().findElement(By.xpath("//div[contains(@class, 'wp-menu-name') and normalize-space(text()) = 'Posts']")).click();
    }
    
    public void btnPostTitle(String title) throws InterruptedException {
    		Thread.sleep(1000);
		getWebDriver().findElement(By.xpath("(//tbody[@id='the-list']//strong//a[starts-with(text(),'" + title + "')])[1]")).click();	
    }
    
    public void addTitle(String title) throws InterruptedException {
		Thread.sleep(1000);
		getWebDriver().findElement(By.xpath("//input[@id='title']")).clear();
		getWebDriver().findElement(By.xpath("//input[@id='title']")).sendKeys("Edited - " + title);
    }
    
    public void addContent(String content) throws InterruptedException {
		Thread.sleep(1000);
		getWebDriver().findElement(By.xpath("//button[@id='content-html']")).click();
		
		Thread.sleep(500);
		getWebDriver().findElement(By.xpath("//textarea[@id='content']")).clear();
		getWebDriver().findElement(By.xpath("//textarea[@id='content']")).sendKeys(content);
    }
    
    public void submitPost() throws InterruptedException {
		Thread.sleep(1000);
		getWebDriver().findElement(By.xpath("//input[@id='publish']")).click();
    }
    
    public void checkCategory(String cat) throws InterruptedException {
    		String path = "//label[contains(@class, 'selectit') and normalize-space(text()) = '" + cat + "']";
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath(path)).click();
    }
    
    public void addPicture(int imageNumber) throws InterruptedException {
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//button[@id='insert-media-button']")).click();
    		
    		Thread.sleep(500);
    		String pathMediaBtn = "//a[contains(@class, 'media-menu-item') and normalize-space(text()) = 'Media Library']";
    		getWebDriver().findElement(By.xpath(pathMediaBtn)).click();

    		Thread.sleep(500);
    		String pathThumbnail = "((//ul[contains(@class, 'attachments')])[1]//div[contains(@class, 'thumbnail')])[" + imageNumber + "]";
    		getWebDriver().findElement(By.xpath(pathThumbnail)).click();

    		Thread.sleep(500);
    		String pathInsertBtn = "//button[contains(@class, 'media-button') and normalize-space(text()) = 'Insert into post']";
    		getWebDriver().findElement(By.xpath(pathInsertBtn)).click();
    		
    		Thread.sleep(500);
    		getWebDriver().findElement(By.xpath("//a[@id='set-post-thumbnail']")).click();
    		
    		Thread.sleep(1000);
    		String pathFeatured = "((//ul[contains(@class, 'attachments')])[3]//div[contains(@class, 'thumbnail')])[" + imageNumber + "]";
    		getWebDriver().findElement(By.xpath(pathFeatured)).click();
    		
    		Thread.sleep(500);
    		String pathFeaturedBtn = "//button[contains(@class, 'media-button') and normalize-space(text()) = 'Set featured image']";
    		getWebDriver().findElement(By.xpath(pathFeaturedBtn)).click();
    		
    }
    
}
