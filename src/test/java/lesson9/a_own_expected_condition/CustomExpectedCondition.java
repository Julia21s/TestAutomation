package lesson9.a_own_expected_condition;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomExpectedCondition {
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
}
