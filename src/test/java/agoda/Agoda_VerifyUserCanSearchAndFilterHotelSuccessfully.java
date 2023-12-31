package agoda;

import base.TestBase;
import com.codeborne.selenide.testng.SoftAsserts;
import org.example.data.agoda.FilterResultData;
import org.example.data.agoda.HotelData;
import org.example.data.agoda.SearchHotelData;
import org.example.page.agoda.HomePage;
import org.example.page.agoda.HotelDetailsPage;
import org.example.page.agoda.ResultPage;
import org.example.page.general.GeneralPage;
import org.example.report.Report;
import org.example.utils.Assertion;
import org.example.utils.Common;
import org.example.utils.WebUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

@Listeners({SoftAsserts.class})
public class Agoda_VerifyUserCanSearchAndFilterHotelSuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        place = "Da Lat";
        threeDaysFromTomorrow = LocalDate.now().plusDays(4);
        tomorrow = LocalDate.now().plusDays(1);
        searchHotelData = SearchHotelData.builder()
                .place(place)
                .fromDate(tomorrow)
                .toDate(threeDaysFromTomorrow)
                .isDayUseStay(false)
                .occupancy(SearchHotelData.Occupancy.builder()
                        .rooms(1)
                        .adult(2)
                        .build())
                .build();
        filterResultData = FilterResultData.builder()
                .roomOffers(List.of("Breakfast included"))
                .build();
    }

    @Test(description = "Verify user can search and filter hotels successfully")
    public void agoda_VerifyUserCanSearchAndFilterHotelSuccessfully() {
        Report.getInstance().step("1. Go to Agoda site");
        generalPage.gotoURL("https://www.agoda.com/");

        Report.getInstance().step("2. Wait for ad display and close it");
        homePage.waitForAdDisplaysAndClose();

        Report.getInstance().step("3. Searh hotel with info: " + searchHotelData);
        homePage.searchHotel(searchHotelData);

        Report.getInstance().step("4. Scroll for more result");
        WebUtils.scrollDownToTheEnd();
        WebUtils.scrollUpToTheTop();

        Assertion.assertTrue(resultPage.areAllTheDestinationsHaveSearchContent(5, place), "VP: Check search Result is displayed correctly with first 5 hotels(destination)");

        Report.getInstance().step("5. Filter hotel: " + filterResultData);
        resultPage.filterHotel(filterResultData);

        Report.getInstance().step("6. Get the first hotel name, destination in result page");
        hotelNameInResultPage = resultPage.getHotelNameList(1).get(0);
        destinationInResultPage = resultPage.getDestinationList(1).get(0);

        Report.getInstance().step("7. Click on first result");
        resultPage.clickOnNthResult(1);
        WebUtils.switchToLastOpenPage();

        Report.getInstance().step("8. Close popup if exist");
        hotelDetailsPage.closeIfPopupExists();

        Report.getInstance().step("9. Get hotel data");
        hotelDataInDetailsPage = hotelDetailsPage.getHotelData();

        Assertion.assertEquals(hotelDataInDetailsPage.getHotelName(), hotelNameInResultPage, "VP: Check The hotel detailed page is displayed with correct name");
        Assertion.assertTrue(hotelDetailsPage.isHotelAddressMatched(hotelDataInDetailsPage.getAddress(), destinationInResultPage), "VP: Check The hotel detailed page is displayed with correct destination");
        Assertion.assertTrue(Common.areAllElementsContainsOption(hotelDetailsPage.getDisplayedTextOfRooms(), "Free breakfast"), "VP: Check The hotel detailed page is displayed with breakfast included");

        Assertion.assertAll("Complete running test case");
    }

    HomePage homePage = new HomePage();
    GeneralPage generalPage = new GeneralPage();
    ResultPage resultPage = new ResultPage();
    HotelDetailsPage hotelDetailsPage = new HotelDetailsPage();
    String place;
    String hotelNameInResultPage;
    String destinationInResultPage;
    LocalDate threeDaysFromTomorrow;
    LocalDate tomorrow;
    FilterResultData filterResultData;
    SearchHotelData searchHotelData;
    List<HotelData> hotelDataList;
    HotelData hotelDataInDetailsPage;
    int minPrice;
    int maxPrice;
}
