import net.bytebuddy.build.Plugin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestSuite1 {
    WebDriver driver;
    @BeforeTest
    public void OpenBrowser()
    {
      //  WebDriver driver = new ChromeDriver(); //local so we need to make it global
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/login");
    }
    @DataProvider(name="testData")
    public static Object [][] userData()
    {
 return new Object[][]{
         {"tomsmith","SuperSecretPassword!"}  , //happy scenario
         {"tomsmith","SuperSecretPassword!!"}, //negative
         {"toomsmith","uperSecretPassword!"} //negative
 };
    }
    @Test(priority = 1,dataProvider = "testData")
    public void ValidLogin(String user,String pass)
    {
        WebElement Username = driver.findElement(By.id("username"));
        //Username.sendKeys("tomsmith");
        Username.sendKeys(user);
        WebElement Password = driver.findElement(By.id("password"));
       // Password.sendKeys("SuperSecretPassword!");
        Password.sendKeys(pass);
        WebElement submit = driver.findElement(By.className("radius"));
        submit.click();
        String Expected_result = "You logged into a secure area!";
        String Actual_result = driver.findElement(By.xpath("//div[@id='flash-messages']")).getText();
        Assert.assertTrue(Actual_result.contains(Expected_result));
        WebElement logging_out = driver.findElement(By.xpath("//a[contains(@class,'secondary')]"));
        logging_out.click();
    }
    @Test(priority = 2)
    public void InvalidLogin()
    {
        WebElement Username = driver.findElement(By.id("username"));
        Username.clear();
        Username.sendKeys("tttomsmith");
        WebElement Password = driver.findElement(By.id("password"));
        Password.clear();
        Password.sendKeys("SuperSecretPassword!");
        WebElement submit = driver.findElement(By.className("radius"));
        submit.click();
        String Expected_result = "You logged into a secure area!";
        String Actual_result = driver.findElement(By.xpath("//div[@id='flash-messages']")).getText();
        Assert.assertFalse(Actual_result.contains(Expected_result));
    }
    /*
     public void Logout()
    {
        WebElement logging_out = driver.findElement(By.xpath("//a[contains(@class,'secondary')]"));
        logging_out.click();
    }
    */

    @AfterTest
    public void CloseBrowser ()
    {
        driver.quit();
    }
}
