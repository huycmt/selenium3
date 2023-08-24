package agoda;

import base.TestBase;
import org.example.data.agoda.FilterResultData;
import org.example.data.agoda.SearchHotelData;
import org.example.page.agoda.HomePage;
import org.example.page.agoda.ResultPage;
import org.example.page.general.GeneralPage;
import org.example.report.Report;
import org.example.utils.Assertion;
import org.example.utils.WebUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class Agoda_VerifyUserCanSearchAndFilterHotelSuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        place = "Da Nang";
        minPrice = 500_000;
        maxPrice = 1_000_000;
        threeDaysFromNextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).plusDays(3);
        searchHotelData = SearchHotelData.builder()
                .place(place)
                .date(threeDaysFromNextFriday)
                .isDayUseStay(true)
                .occupancy(SearchHotelData.Occupancy.builder()
                        .adult(2)
                        .build())
                .build();
        filterResultData = FilterResultData.builder()
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .threeStarCheck(true)
                .build();
    }

    @Test
    public void agoda_VerifyUserCanSearchAndSortHotelSuccessfully() {
        Report.getInstance().step("1. Go to Agoda site");
        generalPage.gotoURL("https://www.agoda.com/");

        Report.getInstance().step("2. Wait for ad display and close it");
        homePage.waitForAdDisplaysAndClose();

        Report.getInstance().step("3. Searh hotel with info: " + searchHotelData);
        homePage.searchHotel(searchHotelData);

        Report.getInstance().step("4. Scroll for more result");
        WebUtils.scrollDownToTheEnd();
        WebUtils.scrollUpToTheTop();

        Assertion.assertTrue(resultPage.areTheFirstDestinationsHaveSearchContent(null, place), "VP: Check the hotel destination is still correct");

        Report.getInstance().step("5. Scroll for more result");
        resultPage.filterHotel(filterResultData);

        Assertion.assertTrue(resultPage.areTheFirstResultsDisplayedWithCorrect(5, place, minPrice, maxPrice, 3.0), "VP: Check search result is displayed correctly with first 5 hotels(destination, price, star). hotel destination is still correct");

        Assertion.assertAll("Complete running test case");
    }

    HomePage homePage = new HomePage();
    GeneralPage generalPage = new GeneralPage();
    ResultPage resultPage = new ResultPage();
    String place;
    LocalDate threeDaysFromNextFriday;
    FilterResultData filterResultData;
    SearchHotelData searchHotelData;
    int minPrice;
    int maxPrice;
}
