package lab.automationpractice.using_page_factory;

import lesson9.a_own_expected_condition.CustomExpectedCondition;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;

    public AccountPage logIn(WebDriver driver, String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickSignInBtn();
        (new WebDriverWait(driver, 15)).until(CustomExpectedCondition.pageIsLoaded("my-account", "My account"));
        return (new AccountPage(driver));
    }
    public void enterUsername(String username){
        (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.visibilityOf(loginFld));
        loginFld.clear();
        loginFld.sendKeys(username);
    }
    public void enterPassword(String password){
        (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.visibilityOf(passwordFld));
        passwordFld.clear();
        passwordFld.sendKeys(password);
    }
    public void clickSignInBtn(){
        (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.elementToBeClickable(signInBtn));
        signInBtn.click();
       }
    public WebDriver getDriver()
    {return this.driver;}

    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement loginFld;

    @FindBy(xpath = "//*[@id=\"passwd\"]")
    private WebElement passwordFld;

    @FindBy(xpath = "//*[@id=\"SubmitLogin\"]/span")
    private WebElement signInBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
