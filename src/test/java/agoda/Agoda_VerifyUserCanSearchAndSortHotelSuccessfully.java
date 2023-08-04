package agoda;

import base.TestBase;

import io.qameta.allure.Allure;
import org.example.page.agoda.HomePage;
import org.example.page.agoda.ResultPage;
import org.example.utils.Assertion;
import org.example.utils.WebUtils;
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

        Allure.step("Scroll for more result");
        WebUtils.scrollTillEndOfThePage();

        Assertion.assertTrue(resultPage.areTheFirstDestinationsHaveSearchContent(5, place), String.format("VP: Check the first 5 destinations have search content: %s", place));

        resultPage.clickLowestPriceFirstButton();

        resultPage.scrollForMoreResults();

        Assertion.assertTrue(resultPage.areTheFirstHotelsSortedWithRightOrder(5), "VP: Check the 5 first hotels are sorted with the right order");
        Assertion.assertTrue(resultPage.areTheFirstDestinationsHaveSearchContent(null, place), "VP: Check the hotel destination is still correct");

        Assertion.assertAll("Complete running test case");

    }

    HomePage homePage = new HomePage();
    ResultPage resultPage = new ResultPage();
    String place;
    LocalDate threeDaysFromNextFriday;
}
