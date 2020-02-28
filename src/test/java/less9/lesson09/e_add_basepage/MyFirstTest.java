package less9.lesson09.e_add_basepage;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyFirstTest extends BaseTest {

    @Test
    public void verifyFirstTipIsCorrectlyUpdatedAfterEnteringNewQuery() {
        LandingPage landingPage = new LandingPage(driver);
        String query1 = "Dress";
        String query2 = "T-shirt";

        landingPage.searchFor(query1);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(landingPage.firstTip));
        Assert.assertThat(landingPage.getFirstTipText(),
                CoreMatchers.containsString(query1));

        landingPage.searchFor(query2);

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElementLocated(landingPage.firstTip, query2));

        Assert.assertThat(
                landingPage.getFirstTipText(),
                CoreMatchers.containsString(query2 + "fdfd"));
    }

}
