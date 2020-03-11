package lab.automationpractice;

import lesson9.a_own_expected_condition.CustomExpectedCondition;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchTest {
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

    @Test
    public void verifyTipIsCorrectlyUpdatedAfterEnteringNewQuery() {

        String tipsXpath = "//*[@id=\"my-account\"]/div[2]/ul/li";
        String textToSearch = "Printed Summer Dress";

        driver.findElement(By.id("search_query_top"))
                .clear();
        driver.findElement(By.id("search_query_top"))
                .sendKeys(textToSearch);
        List<WebElement> tips = driver.findElements(By.xpath(tipsXpath));

        (new WebDriverWait(driver, 15)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(tipsXpath)));

             int tipsQuantity = (int) driver.findElements(By.xpath(tipsXpath)).stream().count();
        Assert.assertThat(tipsQuantity,
                CoreMatchers.equalTo(3));

        WebElement firstTip = (new WebDriverWait(driver, 10))
                .until(CustomExpectedCondition.listNthElementHasText
                        (By.xpath(tipsXpath),
                                1,
                                textToSearch));

        Assert.assertThat(firstTip.getText(),
                CoreMatchers.containsString(textToSearch));

    }

}
