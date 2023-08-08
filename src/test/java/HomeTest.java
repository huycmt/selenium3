import com.codeborne.selenide.*;
import com.codeborne.selenide.testng.SoftAsserts;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;

@Listeners({SoftAsserts.class})
public class HomeTest {

    @Test
    public void testCase() {

        String place = "Da Nang";
        LocalDate threeDaysFromNextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).plusDays(3);

        SelenideConfig selenideConfig = new SelenideConfig().timeout(120000).pollingInterval(200).pageLoadTimeout(120000);
        selenideConfig.assertionMode(AssertionMode.SOFT);
        SelenideDriver selenideDriver = new SelenideDriver(selenideConfig);
        selenideDriver.open("https://www.agoda.com/");
        SelenideElement ad = selenideDriver.$(By.xpath("//button[@aria-label='Close Message']")).should(Condition.exist, Duration.ofSeconds(30));
        ad.click();

        selenideDriver.$(By.xpath("//button[.='Day Use Stays']")).click();
        selenideDriver.$(By.id("textInput")).setValue(place);
        selenideDriver.$(By.xpath("//div[@class='Popup__content']/ul/li[1]")).click();

        String date = threeDaysFromNextFriday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        selenideDriver.$(By.xpath(String.format("//span[@data-selenium-date='%s']", date))).click();
        selenideDriver.$(By.xpath("//div[@data-element-name='occupancy-selector-panel-rooms' and @data-selenium='plus']")).click();
        SelenideElement plusAdult = selenideDriver.$(By.xpath("//div[@data-element-name='occupancy-selector-panel-adult' and @data-selenium='plus']"));
        plusAdult.click();
        plusAdult.click();
        selenideDriver.$(By.id("occupancy-box")).click();

        SelenideElement searchButton = selenideDriver.$(By.xpath("//button[@data-selenium='searchButton']")).should(Condition.exist, Duration.ofSeconds(30));
        searchButton.click();

        ElementsCollection destinationList = selenideDriver.$$(By.xpath("//span[@data-selenium='area-city-text']"));
        List<String> expectedText = Collections.nCopies(destinationList.size(), place);
        destinationList.shouldHave(CollectionCondition.texts(expectedText));

    }
}
