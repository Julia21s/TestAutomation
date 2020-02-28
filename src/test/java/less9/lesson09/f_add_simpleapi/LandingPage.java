package less9.lesson09.f_add_simpleapi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

class LandingPage extends BasePage {

    By searchBox = By.id("search_query_top");
    By tips = By.xpath("//*[@id=\"index\"]/div[2]/ul/li");
    By firstTip = By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]");

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    void openPage() {
        open("http://automationpractice.com/index.php");
    }

    void searchFor(String searchQuery) {
        $(searchBox, ExpectedConditions::elementToBeClickable).clear();
        $(searchBox).sendKeys(searchQuery);

        //$$(tips, 5);
    }

    String getFirstTipText() {
        return $(firstTip).getText();
    }
}
