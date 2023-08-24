package agoda;

import base.TestBase;
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


public class Agoda_VerifyUserCanSearchAndSortHotelSuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        place = "Da Nang";
        threeDaysFromNextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).plusDays(3);
        searchHotelData = SearchHotelData.builder()
                .place(place)
                .date(threeDaysFromNextFriday)
                .isDayUseStay(true)
                .occupancy(SearchHotelData.Occupancy.builder()
                        .adult(2)
                        .build())
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

        Assertion.assertTrue(resultPage.areTheFirstDestinationsHaveSearchContent(5, place), String.format("VP: Check the first 5 destinations have search content: %s", place));

        Report.getInstance().step("5. Click Lowest Price First button");
        resultPage.clickLowestPriceFirstButton();

        Report.getInstance().step("6. Scroll for more result");
        WebUtils.scrollDownToTheEnd();
        WebUtils.scrollUpToTheTop();

        Assertion.assertTrue(resultPage.areTheFirstHotelsSortedWithRightOrder(5), "VP: Check the 5 first hotels are sorted with the right order");
        Assertion.assertTrue(resultPage.areTheFirstDestinationsHaveSearchContent(null, place), "VP: Check the hotel destination is still correct");

        Assertion.assertAll("Complete running test case");
    }

    HomePage homePage = new HomePage();
    GeneralPage generalPage = new GeneralPage();
    ResultPage resultPage = new ResultPage();
    String place;
    LocalDate threeDaysFromNextFriday;
    SearchHotelData searchHotelData;
}
