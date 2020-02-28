package less9.lesson09.e_add_basepage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    WebElement $(By locator) {
        return driver.findElement(locator);
    }

    List<WebElement> $$(By locator) {
        return driver.findElements(locator);
    }
}
