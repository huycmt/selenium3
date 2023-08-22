package vj;

import base.TestBase;
import io.qameta.allure.Allure;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.data.TicketData;
import org.example.data.logigearmail.EmailData;
import org.example.page.vj.HomePage;
import org.example.page.vj.SelectTravelOptionsPage;
import org.example.utils.Assertion;
import org.example.utils.Constants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.example.driver.DriverManager.driver;
import static org.example.utils.ConfigResourceBundle.CONFIG_RESOURCE;

public class VJ_VerifyUserCanSearchAndChooseTicketsOnASpecificDaySuccessfully extends TestBase {

    @BeforeMethod
    public void setUp() {
        departureDate = LocalDate.now().plusDays(1);
        returnDate = LocalDate.now().plusDays(4);
        totalAdult = 2;
        ticketData = TicketData.builder()
                .from("hochiminh")
                .to("hanoi")
                .departureDate(departureDate)
                .returnDate(returnDate)
                .passenger(TicketData.Passenger.builder()
                        .adultNumber(totalAdult)
                        .build())
                .isFindLowestFare(true)
                .build();
    }

    @Test(description = "Search and choose tickets on a specific day successfully")
    public void vj_VerifyUserCanSearchAndChooseTicketsOnASpecificDaySuccessfully() {
        driver().open("https://www.vietjetair.com/en");

        homePage.acceptCookies();
        homePage.clickNotNow();

        homePage.searchTicket(ticketData);

        Assertion.assertTrue(homePage.isSelectTravelOptionsPageDisplayed(), "VP: Verify Select Travel Options page is displayed.");
        Assertion.assertTrue(homePage.isVNDDisplayed(), "VP: Verify the ticket price is displayed in VND");
        Assertion.assertEquals(selectTravelOptionsPage.getColorOfDepartureDate(departureDate), "pink", "VP: Verify the flights dates are displayed correctly (Departure Date)");
        Assertion.assertEquals(selectTravelOptionsPage.getColorOfReturnDate(returnDate), "pink", "VP: Verify the flights dates are displayed correctly (Return Date)");
        Assertion.assertEquals(homePage.getTotalPassenger(), totalAdult, "VP: Verify number of passenger is correct");

        homePage.clickSearch();

        System.out.println("Done");
        Assertion.assertAll("Complete running test case");

    }

    HomePage homePage = new HomePage();
    SelectTravelOptionsPage selectTravelOptionsPage = new SelectTravelOptionsPage();
    TicketData ticketData;
    LocalDate departureDate;
    LocalDate returnDate;
    int totalAdult;

}
