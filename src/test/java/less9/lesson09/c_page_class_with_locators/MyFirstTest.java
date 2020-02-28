package less9.lesson09.c_page_class_with_locators;

import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyFirstTest {

    static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();

        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        driver.get("http://automationpractice.com/index.php");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void verifyFirstTipIsCorrectlyUpdatedAfterEnteringNewQuery() {
        LandingPage landingPage = new LandingPage(/*driver*/);
        String query1 = "Dress";
        String query2 = "T-shirt";

        landingPage.searchFor(query1);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(landingPage.firstTip));
        Assert.assertThat(landingPage.getFirstTipText(),
                CoreMatchers.containsString("Dress"));

        landingPage.searchFor(query2);

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(landingPage.firstTip, "T-shirt"));

        Assert.assertThat(
                landingPage.getFirstTipText(),
                CoreMatchers.containsString("T-shirt"));
    }

    class LandingPage {

        //Don't do so
//        private WebElement searchBox = driver.findElement(By.id("search_query_top"));
//        private WebElement firstTip = driver.findElement(By.xpath());

        private By searchBox = By.id("search_query_top");
        private By firstTip = By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]");

        void searchFor(String searchQuery) {
            $(searchBox).clear();
            $(searchBox).sendKeys(searchQuery);
        }

        String getFirstTipText() {
            return $(firstTip).getText();
        }

        WebElement $(By locator) {
            return driver.findElement(locator);
        }
    }
}
