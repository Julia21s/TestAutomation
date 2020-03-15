package lab.automationpractice.using_page_factory;

import lesson9.a_own_expected_condition.CustomExpectedCondition;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage {
   WebDriver driver;
   public LoginPage signOut(){
        (new WebDriverWait(driver, 15))
               .until(ExpectedConditions.elementToBeClickable(signOut));
       signOut.click();
       (new WebDriverWait(driver, 15)).until(CustomExpectedCondition.pageIsLoaded("authentication", "Login"));
       return (new LoginPage(driver));
   }

    @FindBy(xpath = "//*[@id=\"header\"]/div[2]/div/div/nav/div[2]/a")
    private WebElement signOut;

    //*[@id="header"]/div[2]/div/div/nav/div[2]/a
    public AccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
