package agoda;

import base.TestBase;
import com.codeborne.selenide.testng.SoftAsserts;
import org.example.data.agoda.FilterResultData;
import org.example.data.agoda.HotelData;
import org.example.data.agoda.SearchHotelData;
import org.example.page.agoda.HomePage;
import org.example.page.agoda.ResultPage;
import org.example.page.general.GeneralPage;
import org.example.report.Report;
import org.example.utils.Assertion;
import org.example.utils.WebUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Listeners({SoftAsserts.class})
public class Agoda_VerifyUserCanSearchAndFilterHotelSuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        place = "Da Nang";
        minPrice = 500_000;
        maxPrice = 2_000_000;
        threeDaysFromNextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).plusDays(3);
        searchHotelData = SearchHotelData.builder()
                .place(place)
                .date(threeDaysFromNextFriday)
                .isDayUseStay(true)
                .occupancy(SearchHotelData.Occupancy.builder()
                        .rooms(2)
                        .adult(4)
                        .build())
                .build();
        filterResultData = FilterResultData.builder()
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .threeStarCheck(true)
                .build();
    }

    @Test
    public void agoda_VerifyUserCanSearchAndFilterHotelSuccessfully() {
        Report.getInstance().step("1. Go to Agoda site");
        generalPage.gotoURL("https://www.agoda.com/");

        Report.getInstance().step("2. Wait for ad display and close it");
        homePage.waitForAdDisplaysAndClose();

        Report.getInstance().step("3. Searh hotel with info: " + searchHotelData);
        homePage.searchHotel(searchHotelData);

        Report.getInstance().step("4. Scroll for more result");
        WebUtils.scrollDownToTheEnd();
        WebUtils.scrollDownToTheEnd();
        WebUtils.scrollUpToTheTop();

        Assertion.assertTrue(resultPage.areAllTheDestinationsHaveSearchContent(null, place), "VP: Check the hotel destination is still correct");

        Report.getInstance().step("5. Filter hotel: " + filterResultData);
        resultPage.filterHotel(filterResultData);

        Assertion.assertTrue(resultPage.isSelectedPriceHighlight(), "VP: Check the price filtered is highlighted");
        Assertion.assertTrue(resultPage.isThreeStarFilterApplied(), "VP: Check the star filtered is highlighted");

        Report.getInstance().step("6. Get hotel infos");
        hotelDataList = resultPage.getHotelData(null);

        Assertion.assertTrue(resultPage.areAllHotelInfosMatchWithFilter(hotelDataList, minPrice, maxPrice, place, 3), "VP: Check search result is displayed correctly with first 5 hotels(destination, price, star). hotel destination is still correct");

        Report.getInstance().step("7. Remove price filter");
        resultPage.removePriceFilter();

        Assertion.assertTrue(resultPage.isSliderReset(), "VP: Check the price slice is reset");

        Assertion.assertAll("Complete running test case");
    }

    HomePage homePage = new HomePage();
    GeneralPage generalPage = new GeneralPage();
    ResultPage resultPage = new ResultPage();
    String place;
    LocalDate threeDaysFromNextFriday;
    FilterResultData filterResultData;
    SearchHotelData searchHotelData;
    List<HotelData> hotelDataList;
    int minPrice;
    int maxPrice;
}
