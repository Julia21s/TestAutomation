package lab.automationpractice.buying;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MakingPurchases {
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
    public void buying() throws InterruptedException {
    LandingPage landingPage = new LandingPage(driver);
    String query1 = "Printed Summer Dress"; // Буду покупать такое платье

    landingPage.searchFor(query1);
    landingPage.searchBox.sendKeys(Keys.ENTER);
    (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.visibilityOf((landingPage.chosenProduct)));
    landingPage.chosenProduct.click();
     (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(landingPage.byuButton));
     landingPage.byuButton.click();
     (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[4]/div[1]/div[2]/div[4]/a/span")));
     landingPage.toCart.click();

// Оформление покупки
    (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(landingPage.proceed1));
    landingPage.proceed1.click();
    (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(landingPage.proceed2));
    landingPage.proceed2.click();

    (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(landingPage.proceed3));

    landingPage.terms.click(); // Чекбокс выбран

    landingPage.proceed3.click();

    (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(landingPage.proceedPayment));
    landingPage.proceedPayment.click();
    (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.elementToBeClickable(landingPage.confirmOrder));
    landingPage.confirmOrder.click();


    (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.visibilityOf(landingPage.confirmText));
// Проверим что мы таки купили успешно
    Assert.assertThat(
            landingPage.confirmText.getText(),
            CoreMatchers.containsString("Your order on My Store is complete."));System.out.println("  "+ landingPage.orderNumber.getText());
    //Не нашла ничего лучше, как считать всю таблицу стразу. И взять весь ее текст. И найти в нем номер заказа.
    // Хотя думаю чо есть более элегантное решение

    int refIndex = landingPage.orderNumber.getText().indexOf("reference"); // Ищу слово reference и за ним следует номер заказа
    String orderStr = landingPage.orderNumber.getText().substring(refIndex + 10,refIndex + 19);
    System.out.println(orderStr);
    // Перейдем к ордерам
    driver.findElement(By.xpath("//*[@id=\"center_column\"]/p/a")).click();

    (new WebDriverWait(driver, 15))
            .until(ExpectedConditions.visibilityOf(landingPage.ordersLabel));
// Проверим что номер первого ордера совпадает с моим
    Assert.assertThat(
            landingPage.myOrder.getText(),
            CoreMatchers.containsString(orderStr));
 }

    class LandingPage {

        @FindBy(id = "search_query_top")
        private WebElement searchBox;

        @FindBy(xpath = "//*[@id=\"my-account\"]/div[2]/ul/li[1]")
        private WebElement firstTip;

        @FindBy(xpath = "//*[@id=\"center_column\"]/ul/li[1]/div/div[2]/h5/a")
        private WebElement chosenProduct;

        @FindBy(xpath = "//*[@id=\"add_to_cart\"]/button/span")
        private WebElement byuButton;

        @FindBy(xpath = "/html/body/div/div[1]/header/div[3]/div/div/div[4]/div[1]/div[2]/div[4]/a/span")
        private WebElement toCart;

        @FindBy(xpath = "//*[@id=\"center_column\"]/p[2]/a[1]/span")
        private WebElement proceed1;

        @FindBy(xpath = "//*[@id=\"center_column\"]/form/p/button/span")
        private WebElement proceed2;

        @FindBy(xpath = "//*[@id=\"cgv\"]")
        private WebElement terms;

        @FindBy(xpath = "//*[@id=\"form\"]/p/button/span")
        private WebElement proceed3;

        @FindBy(xpath = "//*[@id=\"HOOK_PAYMENT\"]/div[1]/div/p/a")
        private WebElement proceedPayment;

        @FindBy(xpath = "//*[@id=\"cart_navigation\"]/button/span")
        private WebElement confirmOrder;

        @FindBy(xpath = "//*[@id=\"center_column\"]/div/p/strong")
        private WebElement confirmText;

        @FindBy(xpath = "//*[@id=\"center_column\"]/div")
        private WebElement orderNumber ;

        @FindBy(xpath = "//*[@id=\"center_column\"]/h1")
        private WebElement ordersLabel ;

        @FindBy(xpath = "//*[@id=\"order-list\"]/tbody/tr[1]/td[1]/a")
        private WebElement myOrder ;

        public LandingPage(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        void searchFor(String searchQuery) {
            searchBox.clear();
            searchBox.sendKeys(searchQuery);
        }
    }


}
