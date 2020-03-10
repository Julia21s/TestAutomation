package lesson9.a_own_expected_condition;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomExpectedCondition {
    // pageIsLoaded
    public static ExpectedCondition<String> pageIsLoaded(String expUrl, String expTitle) {
        return new ExpectedCondition<String>() {

            @NullableDecl
            @Override
            public String apply(@NullableDecl WebDriver driver) {
                    return (driver.getCurrentUrl().contains(expUrl)&&(driver.getTitle().contains(expTitle)) ? driver.getWindowHandle(): null);
             }

            @Override
            public String toString() {
                return String.format("Failed to find page %s to have in url: %s, in title: %s\n" ,
                       expUrl, expTitle);
            }
        };
    }
    public static ExpectedCondition<WebElement> listNthElementHasText(
            By locator, int no, String expTextPart) {
        return new ExpectedCondition<WebElement>() {
            private String nthElementText = "";
            @NullableDecl
            @Override
            public WebElement apply(@NullableDecl WebDriver driver) {
                try {

                    WebElement element = driver.findElements(locator).get(no);
                    nthElementText = element.getText();

                    return nthElementText.contains(expTextPart) ? element : null;
                }catch (IndexOutOfBoundsException e){
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("%dth element \nof list \nto have text: %s\n while actual text was: %s\n" ,
                        no, expTextPart, nthElementText);
            }
        };
    }
    //stalenessOfElement(WebElement elToBeDisappeared)
    public static ExpectedCondition<Boolean> stalenessOfElement(WebElement elToBeDisappeared) {
        return new ExpectedCondition<Boolean>() {

            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver driver) {
                    return elToBeDisappeared.isDisplayed() ? false : true;

            }

            @Override
            public String toString() {
                return String.format("%s element does not exists\n" ,
                        elToBeDisappeared.getText());
            }
        };
    }
}
