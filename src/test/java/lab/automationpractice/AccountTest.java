package lab.automationpractice;

import lesson9.a_own_expected_condition.CustomExpectedCondition;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AccountTest {
    static WebDriver driver;

    @BeforeClass
    public static void loginToPage() {
        driver = new ChromeDriver();
        String loginLink = "//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a";
        String LoginField = "//*[@id=\"email\"]";
        String LoginPswd = "//*[@id=\"passwd\"]";

        String authLogin = "lte33@ukr.net";
        String authPswd = "testauto";
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/index.php");
        // Log in to the page
        ;
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        driver.findElement(By.xpath (loginLink)).click();
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(LoginField)));
        driver.findElement(By.xpath (LoginField))
                .clear();
        driver.findElement(By.xpath (LoginField)).sendKeys(authLogin);
        driver.findElement(By.xpath (LoginPswd)).clear();
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(LoginPswd)));
        driver.findElement(By.xpath (LoginPswd)).sendKeys(authPswd);
        driver.findElement(By.xpath ("//*[@id=\"SubmitLogin\"]/span")).click();
        System.out.println("Page title: " + driver.getTitle());

    }

    @AfterClass
    public static void tearDown() {
        //Logout
        driver.findElement(By.xpath ("//*[@id=\"header\"]/div[2]/div/div/nav/div[2]/a")).click();
        driver.quit();
    }
//"ORDER HISTORY AND DETAILS"
    @Test
    public void historyAndDetails() {
        LandingPage landingPage = new LandingPage(driver);
       verifyPageLoadedbyClick(landingPage.historyLink, "history", "Order history");
    }
    //"MY CREDIT SLIPS"
    @Test
    public void creditSlips() {
        LandingPage landingPage = new LandingPage(driver);
        verifyPageLoadedbyClick(landingPage.myCreditTipsLink, "order-slip", "Order slip");
       }
    @Test
    public void address() {
        LandingPage landingPage = new LandingPage(driver);
        verifyPageLoadedbyClick(landingPage.myAddressLink, "addresses", "Addresses");
    }
    @Test
    public void personal() {
        LandingPage landingPage = new LandingPage(driver);
        verifyPageLoadedbyClick(landingPage.myPersonalInfoLink, "identity", "Identity");
    }
    @Test
    public void myWishList() {
        LandingPage landingPage = new LandingPage(driver);
        verifyPageLoadedbyClick(landingPage.myWishListLink, "mywishlist", "My Store");
    }

    public void verifyPageLoadedbyClick(WebElement link, String expUrl, String pageTitlePattern) {

        LandingPage landingPage = new LandingPage(driver);
        (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.elementToBeClickable(landingPage.accountLink));
        landingPage.accountLink.click();
        (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.elementToBeClickable(link));
        link.click();
        String correctPageLoaded = (new WebDriverWait(driver, 10))
                .until(CustomExpectedCondition.pageIsLoaded(expUrl, pageTitlePattern));
        Assert.assertThat("Failed to load correct page",correctPageLoaded, CoreMatchers.notNullValue());

    }

    class LandingPage {

        @FindBy(id = "search_query_top")
        private WebElement searchBox;

        @FindBy(xpath = "//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a/span")
        private WebElement accountLink;

        @FindBy(xpath = "//*[@id=\"center_column\"]/div/div[1]/ul/li[1]/a/span")
        private WebElement historyLink;

        @FindBy(xpath = "//*[@id=\"columns\"]/div[1]/span[3]")
        private WebElement historyValidation;

        @FindBy(xpath = "//*[@id=\"center_column\"]/div/div[1]/ul/li[2]/a/span")
        private WebElement myCreditTipsLink;

        @FindBy(xpath = "//*[@id=\"center_column\"]/div/div[1]/ul/li[3]/a/span")
        private WebElement myAddressLink;

        @FindBy(xpath = "//*[@id=\"center_column\"]/div/div[1]/ul/li[4]/a/span")
        private WebElement myPersonalInfoLink;

        @FindBy(xpath = "//*[@id=\"center_column\"]/div/div[2]/ul/li/a/span")
        private WebElement myWishListLink;

        public LandingPage(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

    }
}
