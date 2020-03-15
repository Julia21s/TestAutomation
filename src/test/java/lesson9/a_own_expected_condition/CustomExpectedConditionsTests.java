package lesson9.a_own_expected_condition;

import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class CustomExpectedConditionsTests {

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
    public void verifyCorrectPageWasLoaded() {

        String correctPageLoaded = (new WebDriverWait(driver, 10))
                .until(CustomExpectedCondition.pageIsLoaded(".com", "My Store"));
        Assert.assertThat("Failed to load correct page",correctPageLoaded,CoreMatchers.notNullValue());
        System.out.println(correctPageLoaded);

    }

    @Test(timeout = 5000l)
    public void verifyFirstTipIsCorrectlyUpdatedAfterEnteringNewQuery() {

        driver.findElement(By.id("search_query_top"))
                .clear();
        driver.findElement(By.id("search_query_top"))
                .sendKeys("Dress");

        WebElement firstTip = (new WebDriverWait(driver, 10))
                .until(CustomExpectedCondition.listNthElementHasText
                        (By.xpath("//*[@id=\"index\"]/div[2]/ul/li"),
                                1,
                                "Dress"));

        Assert.assertThat(firstTip.getText(),
                CoreMatchers.containsString("Dress"));

    }

    @Test(timeout = 5000l)

    public void verifyElementDisapearsFromList() {
        driver.findElement(By.id("search_query_top"))
                .clear();
        driver.findElement(By.id("search_query_top"))
                .sendKeys("Dress");

        Assert.assertTrue("5th ip was not found",
                driver.findElement(
                        By.xpath("//*[@id=\"index\"]/div[2]/ul/li[5]"))
                        .getText().contains("Dress"));
        //Теперь удалим 5й элемент
      //  driver.findElement(By.id("search_query_top")).clear();
        driver.findElement(By.id("search_query_top"))
                .sendKeys(Keys.ESCAPE);
        Boolean elDis = (new WebDriverWait(driver, 10))
                .until(CustomExpectedCondition.stalenessOfElement( driver.findElement(
                        By.xpath("//*[@id=\"index\"]/div[2]/ul/li[5]"))));
        Assert.assertTrue("Still exists",elDis);
    }
}
