package less9.lesson09.a_page_factory_and_lazy_proxy_lements;

import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
        LandingPage landingPage = new LandingPage(driver);
        String query1 = "Dress";
        String query2 = "T-shirt";

        landingPage.searchFor(query1);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(landingPage.firstTip));
        Assert.assertThat(landingPage.firstTip.getText(),
                CoreMatchers.containsString("Dress"));

        landingPage.searchFor(query2);

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(landingPage.firstTip, "T-shirt"));

        Assert.assertThat(
                landingPage.firstTip.getText(),
                CoreMatchers.containsString("T-shirt"));
    }

    class LandingPage {

        @FindBy(id = "search_query_top")
        private WebElement searchBox;

        @FindBy(xpath = "//*[@id=\"index\"]/div[2]/ul/li[1]")
        private WebElement firstTip;

        public LandingPage(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        void searchFor(String searchQuery) {
            searchBox.clear();
            searchBox.sendKeys(searchQuery);
        }
    }
}
