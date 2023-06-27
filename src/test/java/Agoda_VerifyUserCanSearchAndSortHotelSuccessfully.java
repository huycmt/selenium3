import base.TestBase;
import com.codeborne.selenide.*;

//import static org.example.element.Element.$x;

import org.example.driver.DriverManager;
import org.example.element.Element;
import org.example.page.agoda.HomePage;
import org.example.page.agoda.ResultPage;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.example.driver.DriverManager.driver;

public class Agoda_VerifyUserCanSearchAndSortHotelSuccessfully extends TestBase {

    @Test
    public void agoda_VerifyUserCanSearchAndSortHotelSuccessfully() {

        driver().open("https://www.agoda.com/");

        place = "Da Nang";
        threeDaysFromNextFriday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        homePage.waitForAdDisplaysAndClose();

        homePage.clickDayUseStay();

        homePage.enterPlaceTextBox(place);
        homePage.clickFirstResult();

        homePage.selectDate(threeDaysFromNextFriday);

        homePage.setOccupancy(2, 4 , null);

        homePage.clickSearch();

        resultPage.isAllDestinationHaveSearchContent(place);

    }

    HomePage homePage = new HomePage();
    ResultPage resultPage = new ResultPage();
    String place;
    LocalDate threeDaysFromNextFriday;


}
