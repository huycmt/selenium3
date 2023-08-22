package org.example.page.vj;

import io.qameta.allure.Step;
import org.example.data.TicketData;
import org.example.element.Element;
import org.example.utils.WebUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static org.example.utils.ConfigResourceBundle.CONFIG_RESOURCE;
import static org.example.utils.ConfigResourceBundle.LOCALE;

public class HomePage {

    @Step("Accept cookies")
    public void acceptCookies() {
        acceptCookiesButton.click();
    }

    @Step("Click not now button")
    public void clickNotNow() {
        WebUtils.switchToFrame(iframe);
        notNowButton.click();
        WebUtils.switchToMain();
    }

    @Step("Search ticket with info")
    public void searchTicket(TicketData ticketData) {
        fillTicketInfo(ticketData);
        clickSearch();
    }

    @Step("Fill ticket information")
    public void fillTicketInfo(TicketData ticketData) {
        if (ticketData.isReturn()) {
            roundTripRadio.click();
        }
        if (Objects.nonNull(ticketData.getFrom())) {
            divLabelText.set(CONFIG_RESOURCE.getValue("from"));
            divLabelText.click();
            divText.set(CONFIG_RESOURCE.getValue(ticketData.getFrom()));
            divText.click();
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(ticketData.getTo())) {
            divLabelText.set(CONFIG_RESOURCE.getValue("to"));
            divLabelText.click();
            divText.set(CONFIG_RESOURCE.getValue(ticketData.getTo()));
            divText.click();
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(ticketData.getDepartureDate())) {
            divText.set(CONFIG_RESOURCE.getValue("departureDate"));
            divText.click();
            selectDatePicker(ticketData.getDepartureDate());
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(ticketData.getReturnDate())) {
            divText.set(CONFIG_RESOURCE.getValue("returnDate"));
            divText.click();
            selectDatePicker(ticketData.getReturnDate());
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(ticketData.getPassenger())) {
            TicketData.Passenger passenger = ticketData.getPassenger();
            divLabelText.set(CONFIG_RESOURCE.getValue("passenger"));
            divLabelText.click();
            if (Objects.nonNull(passenger.getAdultNumber()) && passenger.getAdultNumber() > 1) {
                for (int i = 1; i < passenger.getAdultNumber(); i++) {
                    plusButton.set(CONFIG_RESOURCE.getValue("adult"));
                    plusButton.click();
                }
            }
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(ticketData.isFindLowestFare()) && ticketData.isFindLowestFare()) {
            findLowestFare.set(CONFIG_RESOURCE.getValue("findLowestFare"));
            findLowestFare.click();
        }
    }

    @Step("Click search flight button")
    public void clickSearch() {
        searchButton.set(CONFIG_RESOURCE.getValue("letsgo"));
        searchButton.click();
    }

    public void selectDatePicker(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", LOCALE);
        String monthYear = localDate.format(formatter);
        date.set(monthYear, localDate.getDayOfMonth());
        date.click();
    }

    public boolean isVNDDisplayed() {
        return vnd.isDisplayed();
    }

    public LocalDate getDepartureDateDisplays() {
        displayDate.set(CONFIG_RESOURCE.getValue("departureDate"));
        return LocalDate.parse(displayDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getReturnDateDisplays() {
        displayDate.set(CONFIG_RESOURCE.getValue("returnDate"));
        return LocalDate.parse(displayDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public int getTotalPassenger() {
        passengerDescribe.set(CONFIG_RESOURCE.getValue("passenger"));
        return Integer.parseInt(passengerDescribe.getAttribute("value").split(" ")[0]);
    }

    public boolean isSelectTravelOptionsPageDisplayed() {
        searchButton.set(CONFIG_RESOURCE.getValue("letsgo"));
        divLabelText.set(CONFIG_RESOURCE.getValue("from"));
        divText.set(CONFIG_RESOURCE.getValue("returnDate"));
        return divLabelText.isDisplayed() && divText.isDisplayed() && searchButton.isDisplayed();
    }



    Element acceptCookiesButton = new Element("xpath=//div[contains(@class,'MuiDialogContent-root')]//h5", true);
    Element notNowButton = new Element("id=__st_bpn_no", true);
    Element roundTripRadio = new Element("xpath=//input[@value='roundTrip']", true);
    Element divText = new Element("xpath=//div[.='%s']", true);
    Element divLabelText = new Element("xpath=//div[label[.='%s']]", true);
    Element date = new Element("xpath=(//div[div[@class='rdrMonthName' and .='%s']]//button[not(contains(@class,'rdrDayPassive'))]//span[.='%d'])[1]", true);
    Element plusButton = new Element("xpath=//div[div[div[p[.='%s']]]]//button[2]", true);
    Element findLowestFare = new Element("xpath=//div[h3[.='%s']]//input", true);
    Element outsideOfPPopup = new Element("xpath=//input[@value='roundTrip']/../../../../../../..", true);
    Element searchButton = new Element("xpath=//span[.=\"%s\"]", true);
    Element vnd = new Element("xpath=//span[.='VND']", true);
    Element iframe = new Element("xpath=//div[@id='__st_fancy_popup']/iframe", true);
    Element displayDate = new Element("xpath=//div[p[.='%s']]/p[2]", true);
    Element passengerDescribe = new Element("xpath=//div[label[.='%s']]//input", true);
}
