package agoda;

import base.TestBase;
import io.qameta.allure.Allure;
import org.example.data.agoda.FilterResultData;
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

public class Agoda_VerifyUserCanSearchAndFilterHotelSuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        place = "Da Nang";
        minPrice = 500_000;
        maxPrice = 1_000_000;
        threeDaysFromNextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).plusDays(3);
        filterResultData = FilterResultData.builder()
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .threeStarCheck(true)
                .build();
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

        Assertion.assertTrue(resultPage.areTheFirstDestinationsHaveSearchContent(null, place), "VP: Check the hotel destination is still correct");

        resultPage.filterHotel(filterResultData);

        Assertion.assertTrue(resultPage.areTheFirstResultsDisplayedWithCorrect(5, place, minPrice, maxPrice, 3.0), "VP: Check search result is displayed correctly with first 5 hotels(destination, price, star). hotel destination is still correct");


        Assertion.assertAll("Complete running test case");

    }

    HomePage homePage = new HomePage();
    ResultPage resultPage = new ResultPage();
    String place;
    LocalDate threeDaysFromNextFriday;
    FilterResultData filterResultData;
    int minPrice;
    int maxPrice;
}
