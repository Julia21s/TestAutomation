package lesson08.d_implicitly_waits_vs_refreshed_elements;

import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyFirstTest {

    static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        driver.get("http://automationpractice.com/index.php");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test(timeout = 5000l)
    public void verifyFirstTipIsCorrectlyUpdatedAfterEnteringNewQuery() {
        driver.findElement(By.id("search_query_top"))
                .clear();
        driver.findElement(By.id("search_query_top"))
                .sendKeys("Dress");

        Assert.assertThat(
                driver
                        .findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]"))
                        .getText(),
                CoreMatchers.containsString("Dress"));

        driver.findElement(By.id("search_query_top"))
                .clear();
        driver.findElement(By.id("search_query_top"))
                .sendKeys("T-shirt");
        waitForElementDisappearing(By.xpath("//*[@id='index']/div[2]/ul/li[position()=1 and contains(text(), 'Dress')]"), 5000l);
        Assert.assertThat(
                driver
                        .findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]"))
                        .getText(),
                CoreMatchers.containsString("T-shirt"));
    }

    @Test(timeout = 5000l)
    @Ignore
    public void verifyFirstTipIsCorrect_viaAssertTrue() {
        driver.findElement(By.id("search_query_top"))
                .clear();
        driver.findElement(By.id("search_query_top"))
                .sendKeys("Dress");

        Assert.assertTrue("First tip text was wrong",
                driver.findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]"))
                        .getText().contains("Dress1"));
    }

    void waitForElementDisappearing(By locator, long timeout) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        long startTime = System.currentTimeMillis();
        while(!driver.findElements(locator).isEmpty()) {
            if(System.currentTimeMillis() - startTime > timeout) {
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                throw new TimeoutException("Element is still present");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }
}
