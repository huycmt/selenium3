package agoda;

import base.TestBase;
import org.example.data.agoda.FilterResultData;
import org.example.data.agoda.HotelData;
import org.example.data.agoda.SearchHotelData;
import org.example.page.agoda.*;
import org.example.page.general.GeneralPage;
import org.example.report.Report;
import org.example.utils.Assertion;
import org.example.utils.Common;
import org.example.utils.WebUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class Agoda_VerifyUserCanAddHotelIntoFavouriteSuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        place = "Da Lat";
        threeDaysFromNextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).plusDays(3);
        nextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        email = System.getProperty("agodamail");
        password = System.getProperty("agodapass");
        searchHotelData = SearchHotelData.builder()
                .place(place)
                .fromDate(nextFriday)
                .toDate(threeDaysFromNextFriday)
                .isDayUseStay(false)
                .occupancy(SearchHotelData.Occupancy.builder()
                        .rooms(2)
                        .adult(4)
                        .build())
                .build();
        filterResultData = FilterResultData.builder()
                .facilities(List.of("Swimming pool"))
                .build();
    }

    @Test(description = "Verify user can add hotel into Favourite successfully")
    public void agoda_VerifyUserCanAddHotelIntoFavouriteSuccessfully() {
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
        hotelDataInResultsPage = HotelData.builder()
                .hotelName(hotelNameInResultPage)
                .address(destinationInResultPage.split("-")[0].trim())
                .build();

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

        Report.getInstance().step("10. Add the hotel into the saved list");
        hotelDetailsPage.clickFavoriteHeartIcon();

        Assertion.assertTrue(loginPage.isLoginPageDisplayed(), "VP: Check login popup appears");

        Report.getInstance().step("11. Log in Agoda");
        loginPage.login(email, password);

        Report.getInstance().step("12. Add the hotel into the saved list again in case hotel is not added to favorite yet");
        hotelDetailsPage.clickFavoriteHeartIcon();

        Report.getInstance().step("13. Open Saved Properties List Page");
        homePage.selectHeaderMenu("Saved properties list");

        Report.getInstance().step("14. Click on first result");
        savePropertiesListPage.clickFirstResult();
        searchHotelData.setPlace(null);

        Assertion.assertEquals(savePropertiesListPage.getSearchHotelData(), searchHotelData, "VP: The hotel is added to the saved list successfully with the correct booking dates and the correct number of guests");
        Assertion.assertEquals(savePropertiesListPage.getHotelData(), hotelDataInResultsPage, "VP: The hotel is added to the saved list successfully with the correct hotel information");

        Assertion.assertAll("Complete running test case");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        Report.getInstance().step("Post condition: Remove hotel from favorite");
        savePropertiesListPage.unFavoriteCard();
    }

    HomePage homePage = new HomePage();
    GeneralPage generalPage = new GeneralPage();
    ResultPage resultPage = new ResultPage();
    LoginPage loginPage = new LoginPage();
    HotelDetailsPage hotelDetailsPage = new HotelDetailsPage();
    SavePropertiesListPage savePropertiesListPage = new SavePropertiesListPage();
    String place;
    String email;
    String password;
    String hotelNameInResultPage;
    String destinationInResultPage;
    LocalDate threeDaysFromNextFriday;
    LocalDate nextFriday;
    FilterResultData filterResultData;
    SearchHotelData searchHotelData;
    HotelData hotelDataInDetailsPage;
    HotelData hotelDataInResultsPage;
}
