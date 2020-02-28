package less9.lesson09.e_add_basepage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class LandingPage extends BasePage{

    By searchBox = By.id("search_query_top");
    By firstTip = By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]");

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    void searchFor(String searchQuery) {
        $(searchBox).clear();
        $(searchBox).sendKeys(searchQuery);
    }

    String getFirstTipText() {
        return $(firstTip).getText();
    }
}
