package lab.automationpractice.using_page_factory;

import lesson9.a_own_expected_condition.CustomExpectedCondition;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PageFactoryTests {
    static WebDriver driver; //= new ChromeDriver();
    static AccountPage accountPage;
    static LoginPage loginPage;
    String username = "lte33@ukr.net";
    String password = "testauto";
    String loginLink = "//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a";

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

    @Test
   // @Ignore
    public void Test1_loginToAccountTest1(){

        (new WebDriverWait(driver,15)).until(ExpectedConditions.elementToBeClickable(By.xpath(loginLink)));
        driver.findElement(By.xpath(loginLink)).click();
        (new WebDriverWait(driver, 15)).until(CustomExpectedCondition.pageIsLoaded("authentication", "Login"));
        loginPage = new LoginPage(driver);
        accountPage = loginPage.logIn(driver,username,password);
        Assert.assertTrue("My account page is not loaded",driver.getTitle().contains("My account"));

       }
    @Test
    @Ignore
    public void Test1_loginToAccountTest2(){

        (new WebDriverWait(driver,15)).until(ExpectedConditions.elementToBeClickable(By.xpath(loginLink)));
        driver.findElement(By.xpath(loginLink)).click();
        (new WebDriverWait(driver, 15)).until(CustomExpectedCondition.pageIsLoaded("authentication", "Login"));
        loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickSignInBtn();
        (new WebDriverWait(driver, 15)).until(CustomExpectedCondition.pageIsLoaded("my-account", "My account"));
        accountPage = new AccountPage(driver);
        Assert.assertTrue("My account page is not loaded",driver.getTitle().contains("My account"));
    }

    @Test
    public void Test3_signOutTest(){
       loginPage = accountPage.signOut();
       Assert.assertTrue("Login page is not loaded",driver.getTitle().contains("Login"));
    }

}
