package less9.lesson09.d_add_basetest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

class LandingPage {

    //Don't do so
//        private WebElement searchBox = driver.findElement(By.id("search_query_top"));
//        private WebElement firstTip = driver.findElement(By.xpath());

    By searchBox = By.id("search_query_top");
    By firstTip = By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]");

    void searchFor(String searchQuery) {
        $(searchBox).clear();
        $(searchBox).sendKeys(searchQuery);
    }

    String getFirstTipText() {
        return $(firstTip).getText();
    }

    WebElement $(By locator) {
        return MyFirstTest.driver.findElement(locator);
    }
}
