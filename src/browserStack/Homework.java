package browserStack;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.lang.Thread;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
class IPhone implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("device", "iPhone 12 Pro");
		capsHashtable.put("real_mobile", "true");
		capsHashtable.put("os_version", "14");
		capsHashtable.put("build", "Homework Build");
		capsHashtable.put("name", "IPhone");
		Homework r1 = new Homework();
		r1.executeTest(capsHashtable);
  }
}
class Chrome implements Runnable {
  public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browserName", "Chrome");
		capsHashtable.put("browser_version", "latest");
		capsHashtable.put("os", "Windows");
		capsHashtable.put("os_version", "10");
		capsHashtable.put("build", "Homework Build");
		capsHashtable.put("name", "Chrome");
		Homework r1 = new Homework();
    r1.executeTest(capsHashtable);
  }
}
class Safari implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browser", "safari");
		capsHashtable.put("browser_version", "14");
		capsHashtable.put("os", "OS X");
		capsHashtable.put("os_version", "Big Sur");
		capsHashtable.put("build", "Homework Build");
		capsHashtable.put("name", "Safari");
		Homework r1 = new Homework();
    r1.executeTest(capsHashtable);
  }
}
class FireFox implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browserName", "Firefox");
		capsHashtable.put("browser_version", "latest");
		capsHashtable.put("os", "Windows");
		capsHashtable.put("os_version", "7");
		capsHashtable.put("build", "Homework Build");
		capsHashtable.put("name", "FireFox");
		Homework r1 = new Homework();
    r1.executeTest(capsHashtable);
  }
}
class Edge implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browserName", "Edge");
		capsHashtable.put("browser_version", "latest");
		capsHashtable.put("os", "Windows");
		capsHashtable.put("os_version", "10");
		capsHashtable.put("build", "Homework Build");
		capsHashtable.put("name", "Edge");
		Homework r1 = new Homework();
    r1.executeTest(capsHashtable);
  }
}
public class Homework {

  public static final String USERNAME = "leevardaro_sqiF2Y";
  public static final String AUTOMATE_KEY = "XNR17aPXPp6NaNZNvyD2";
  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
  public static final String baseUrl = "https://jobs.workable.com/";
  //variables for assertions
  public static final String expectedTitle = "Job Search - Job Finder - Job Listings | Workable for Job Seekers";
  public static final String expJob = "Customer Engineer- San Francisco";
  @Test
  public static void main(String[] args) throws Exception {
	Thread object1 = new Thread(new IPhone());
    object1.start();
    Thread object2 = new Thread(new Chrome());
    object2.start();
    Thread object3 = new Thread(new Safari());
    object3.start();
    Thread object4 = new Thread(new FireFox());
    object4.start();
    Thread object5 = new Thread(new Edge());
    object5.start();
  }
	public void executeTest(Hashtable<String, String> capsHashtable) {
		String key;
	  DesiredCapabilities caps = new DesiredCapabilities();
		// Iterate over the hashtable and set the capabilities
		Set<String> keys = capsHashtable.keySet();
    Iterator<String> itr = keys.iterator();
    while (itr.hasNext()) {
       key = itr.next();
       caps.setCapability(key, capsHashtable.get(key));
    }
    WebDriver driver = null;
	try {
		driver = new RemoteWebDriver(new URL(URL), caps);
	} catch (MalformedURLException e1) {
		e1.printStackTrace();
	}
    JavascriptExecutor jse = (JavascriptExecutor)driver;

		try {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        // launch browsers on job search site
        driver.get(baseUrl);
        String actualTitle = driver.getTitle();
        
        //assertion 1 
        Assert.assertEquals(expectedTitle, actualTitle);
    
        //input job
        WebElement jobSearch = driver.findElement(By.xpath("//*[@data-ui=\'search-input\']"));
        jobSearch.sendKeys("browserstack customer experience engineer");
        //input location and submit
        WebElement locSearch = driver.findElement(By.xpath("//*[@data-ui=\'location-input\']"));
        locSearch.sendKeys("San Francisco");
        locSearch.submit();
        // get rid of cookie bar
        WebElement declineLink = driver.findElement(By.xpath("//*[@id='hs-eu-decline-button']"));
        declineLink.click();
        //click on job
        WebElement jobLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[.='Customer Engineer- San Francisco']/..//a[@data-ui='job-view']")));
        Boolean isPresent = driver.findElements(By.xpath("//h3[.='Customer Engineer- San Francisco']")).size() > 0;

        //assertion 2
        Assert.assertTrue(isPresent);
        
        jobLink.click();
        //verify job
        WebElement jobTitle = driver.findElement(By.xpath("//a[@href='http://www.browserstack.com']/../h1[@data-ui=\"preview-job-title\"]"));
        String actualJob = jobTitle.getText();
        
        //assertion 3 passes test	    
	    Assert.assertEquals(actualJob, expJob);
	    

	    jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Correct Job!\"}}");
	    
	    //close driver
	    driver.quit();
		}
		catch (Exception e) {
			e.printStackTrace();
			jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"Test Failed!\"}}");
		    
		    //close driver
		    driver.quit();	
		}
		}
	}
