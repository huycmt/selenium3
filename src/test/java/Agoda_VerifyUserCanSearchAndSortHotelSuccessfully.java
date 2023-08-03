import base.TestBase;

import org.example.page.agoda.HomePage;
import org.example.page.agoda.ResultPage;
import org.example.utils.Assertion;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.example.driver.DriverManager.driver;

public class Agoda_VerifyUserCanSearchAndSortHotelSuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        place = "Da Nang";
        threeDaysFromNextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).plusDays(3);
    }

    @Test
    public void agoda_VerifyUserCanSearchAndSortHotelSuccessfully() {
        driver().open("https://www.agoda.com/");

        homePage.waitForAdDisplaysAndClose();

        homePage.clickDayUseStay();

        homePage.enterPlaceTextBox(place);
        homePage.clickFirstResult();
        homePage.selectDate(threeDaysFromNextFriday);
        homePage.setOccupancy(2, 4 , null);

        homePage.clickSearch();

        Assertion.assertTrue(resultPage.isAllDestinationHaveSearchContent(place), "VP: Check all destination have search content: " + place);

        Assertion.assertAll("Complete running test case");

    }

    HomePage homePage = new HomePage();
    ResultPage resultPage = new ResultPage();
    String place;
    LocalDate threeDaysFromNextFriday;
}
