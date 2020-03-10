package lab.automationpractice;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AllTipsContainText {
    static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        driver.get("http://automationpractice.com/index.php");
    }

    @AfterClass

    public static void tearDown() {
        driver.quit();
    }

    @Test(timeout = 5000l)
    //@Ignore
    public void verifyAllTipIsCorrectlyUpdatedAfterEnteringNewQuery() {
        String matchStr = "Dress";
        driver.findElement(By.id("search_query_top"))
                .clear();
        driver.findElement(By.id("search_query_top"))
                .sendKeys(matchStr);
        (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")));
        //просто потренироваться
       driver.findElements(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")).stream().forEach(s -> System.out.println(s.getText()));
       driver.findElements(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")).stream().filter(s -> !s.getText().contains(matchStr)).forEach(s -> System.out.println("This one doesn't match " + s.getText()));
       // а теперь проверяю
       Assert.assertTrue("Validating containing substring " + matchStr + " failed", driver.findElements(By.xpath("//*[@id=\"index\"]/div[2]/ul/li")).stream().allMatch(s -> s.getText().equals(matchStr)));

    }

 }
