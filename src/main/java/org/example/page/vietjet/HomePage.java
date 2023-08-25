package org.example.page.vietjet;

import org.example.data.vietjet.SearchTicketData;
import org.example.element.Element;
import org.example.utils.WebUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.example.utils.ConfigResourceBundle.CONFIG_RESOURCE;
import static org.example.utils.ConfigResourceBundle.LOCALE;

public class HomePage {

    public void acceptCookies() {
        acceptCookiesButton.click();
    }

    public void clickNotNow() {
        WebUtils.switchToFrame(iframe);
        notNowButton.click();
        WebUtils.switchToMain();
    }

    public void searchTicket(SearchTicketData searchTicketData) {
        fillTicketInfo(searchTicketData);
        clickSearch();
    }

    public void fillTicketInfo(SearchTicketData searchTicketData) {
        if (searchTicketData.isReturn()) {
            roundTripRadio.click();
        }
        if (Objects.nonNull(searchTicketData.getFrom())) {
            divLabelText.set(CONFIG_RESOURCE.getValue("from"));
            divLabelText.click();
            divText.set(CONFIG_RESOURCE.getValue(searchTicketData.getFrom()));
            divText.click();
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(searchTicketData.getTo())) {
            divLabelText.set(CONFIG_RESOURCE.getValue("to"));
            divLabelText.click();
            divText.set(CONFIG_RESOURCE.getValue(searchTicketData.getTo()));
            divText.click();
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(searchTicketData.getDepartureDate())) {
            divText.set(CONFIG_RESOURCE.getValue("departureDate"));
            divText.click();
            selectDatePicker(searchTicketData.getDepartureDate());
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(searchTicketData.getReturnDate())) {
            divText.set(CONFIG_RESOURCE.getValue("returnDate"));
            divText.click();
            selectDatePicker(searchTicketData.getReturnDate());
            outsideOfPPopup.click();
        }
        if (Objects.nonNull(searchTicketData.getPassenger())) {
            SearchTicketData.Passenger passenger = searchTicketData.getPassenger();
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
        if (Objects.nonNull(searchTicketData.isFindLowestFare()) && searchTicketData.isFindLowestFare()) {
            findLowestFare.set(CONFIG_RESOURCE.getValue("findLowestFare"));
            findLowestFare.click();
        }
    }

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

    private Element acceptCookiesButton = new Element("xpath=//div[contains(@class,'MuiDialogContent-root')]//h5", true);
    private Element notNowButton = new Element("id=__st_bpn_no", true);
    private Element roundTripRadio = new Element("xpath=//input[@value='roundTrip']", true);
    private Element divText = new Element("xpath=//div[.='%s']", true);
    private Element divLabelText = new Element("xpath=//div[label[.='%s']]", true);
    private Element date = new Element("xpath=(//div[div[@class='rdrMonthName' and .='%s']]//button[not(contains(@class,'rdrDayPassive'))]//span[.='%d'])[1]", true);
    private Element plusButton = new Element("xpath=//div[div[div[p[.='%s']]]]//button[2]", true);
    private Element findLowestFare = new Element("xpath=//div[h3[.='%s']]//input", true);
    private Element outsideOfPPopup = new Element("xpath=//input[@value='roundTrip']/../../../../../../..", true);
    private Element searchButton = new Element("xpath=//span[.=\"%s\"]", true);
    private Element vnd = new Element("xpath=//span[.='VND']", true);
    private Element iframe = new Element("xpath=//div[@id='__st_fancy_popup']/iframe", true);
    private Element displayDate = new Element("xpath=//div[p[.='%s']]/p[2]", true);
    private Element passengerDescribe = new Element("xpath=//div[label[.='%s']]//input", true);
}
