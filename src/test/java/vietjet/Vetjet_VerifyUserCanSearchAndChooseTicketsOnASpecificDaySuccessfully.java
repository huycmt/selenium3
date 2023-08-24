package vietjet;

import base.TestBase;
import org.example.data.vietjet.SearchTicketData;
import org.example.data.vietjet.TicketInfoData;
import org.example.page.general.GeneralPage;
import org.example.page.vietjet.HomePage;
import org.example.page.vietjet.PassengerInformationPage;
import org.example.page.vietjet.SelectFightPage;
import org.example.report.Report;
import org.example.utils.Assertion;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

import static org.example.utils.ConfigResourceBundle.CONFIG_RESOURCE;
import static org.example.utils.ConfigResourceBundle.LOCALE;

public class Vetjet_VerifyUserCanSearchAndChooseTicketsOnASpecificDaySuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        departureDate = LocalDate.now().plusDays(1);
        returnDate = LocalDate.now().plusDays(4);
        totalAdult = 2;
        searchTicketData = SearchTicketData.builder()
                .from("hochiminh")
                .to("hanoi")
                .departureDate(departureDate)
                .returnDate(returnDate)
                .passenger(SearchTicketData.Passenger.builder()
                        .adultNumber(totalAdult)
                        .build())
                .build();
    }

    @Test(description = "Search and choose tickets on a specific day successfully")
    public void vietjet_VerifyUserCanSearchAndChooseTicketsOnASpecificDaySuccessfully() {
        Report.getInstance().step("1. Go to Vietjet site");
        generalPage.gotoURL("https://www.vietjetair.com/en");

        Report.getInstance().step("2. Accept cookies and not receive ad");
        homePage.acceptCookies();
        homePage.clickNotNow();

        Report.getInstance().step("3. Search ticket with info: " + searchTicketData);
        homePage.searchTicket(searchTicketData);
        selectFightPage.waitForPricesDisplay();

        Assertion.assertTrue(selectFightPage.isSelectTravelOptionsPageDisplayed(), "VP: Verify Select Travel Options page is displayed.");
        Assertion.assertTrue(selectFightPage.isTheTicketPriceDisplayedIn("VND"), "VP: Verify the ticket price is displayed in VND");
        Assertion.assertEquals(selectFightPage.getSelectedDayOfWeek(), departureDate.getDayOfWeek().getDisplayName(TextStyle.FULL, LOCALE), "VP: Verify the departure flights dates are displayed correctly (day of week)");
        Assertion.assertTrue(selectFightPage.getSelectedMonthDay().contains(departureDate.format(DateTimeFormatter.ofPattern("MMMM dd", LOCALE))), "VP: Verify the departure flights dates are displayed correctly (month and date)");
        Assertion.assertEquals(selectFightPage.getFrom(), CONFIG_RESOURCE.getValue("hochiminh"), "VP: Verify the departure place is correct");
        Assertion.assertEquals(selectFightPage.getTo(), CONFIG_RESOURCE.getValue("hanoi"), "VP: Verify the arrival place is correct");
        Assertion.assertEquals(selectFightPage.getTotalPassenger(), totalAdult, "VP: Verify number of passenger is correct");

        Report.getInstance().step("4. Choose the cheapest tickets in Departure flight and click Continue button");
        selectFightPage.selectTheFirstCheapestTicket();
        departureInfo = selectFightPage.getTicketInfoData();
        departureInfo.setFlightDate(departureDate);
        selectFightPage.clickContinue();

        Assertion.assertTrue(selectFightPage.isSelectTravelOptionsPageDisplayed(), "VP: Verify Select Travel Options page is displayed.");
        Assertion.assertTrue(selectFightPage.isTheTicketPriceDisplayedIn("VND"), "VP: Verify the ticket price is displayed in VND");
        Assertion.assertEquals(selectFightPage.getSelectedDayOfWeek(), returnDate.getDayOfWeek().getDisplayName(TextStyle.FULL, LOCALE), "VP: Verify the return flights dates are displayed correctly (day of week)");
        Assertion.assertTrue(selectFightPage.getSelectedMonthDay().contains(returnDate.format(DateTimeFormatter.ofPattern("MMMM dd", LOCALE))), "VP: Verify the return flights dates are displayed correctly (month and date)");
        Assertion.assertEquals(selectFightPage.getFrom(), CONFIG_RESOURCE.getValue("hanoi"), "VP: Verify the departure place is correct");
        Assertion.assertEquals(selectFightPage.getTo(), CONFIG_RESOURCE.getValue("hochiminh"), "VP: Verify the arrival place is correct");
        Assertion.assertEquals(selectFightPage.getTotalPassenger(), totalAdult, "VP: Verify number of passenger is correct");

        Report.getInstance().step("5. Choose the cheapest tickets in Return flight and click Continue button");
        selectFightPage.selectTheFirstCheapestTicket();
        returnInfo = selectFightPage.getTicketInfoData();
        returnInfo.setFlightDate(returnDate);
        selectFightPage.clickContinue();

        Assertion.assertTrue(passengerInformationPage.isPassengerInformationPageDisplayed(), "VP: Verify Passenger Information page is displayed");
        Assertion.assertEquals(departureInfo, passengerInformationPage.getTicketInfoOfReservation(true), "VP: Verify tickets information of departure flight is correct");
        Assertion.assertEquals(returnInfo, passengerInformationPage.getTicketInfoOfReservation(false), "VP: Verify tickets information of return flight is correct");

        Assertion.assertAll("Complete running test case");
    }

    HomePage homePage = new HomePage();
    GeneralPage generalPage = new GeneralPage();
    SelectFightPage selectFightPage = new SelectFightPage();
    PassengerInformationPage passengerInformationPage = new PassengerInformationPage();
    SearchTicketData searchTicketData;
    TicketInfoData departureInfo;
    TicketInfoData returnInfo;
    LocalDate departureDate;
    LocalDate returnDate;
    int totalAdult;
}
