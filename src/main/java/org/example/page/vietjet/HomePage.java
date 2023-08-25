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

    /**
     * Click Accept Cookies
     */
    public void acceptCookies() {
        acceptCookiesButton.click();
    }

    /**
     * Click not receive special offer
     */
    public void clickNotNow() {
        WebUtils.switchToFrame(iframe);
        notNowButton.click();
        WebUtils.switchToMain();
    }

    /**
     * Search ticket with info
     *
     * @param searchTicketData
     */
    public void searchTicket(SearchTicketData searchTicketData) {
        fillTicketInfo(searchTicketData);
        clickSearch();
    }

    /**
     * Fill data to search ticket form
     *
     * @param searchTicketData
     */
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

    /**
     * Click Search flight button
     */
    public void clickSearch() {
        searchButton.set(CONFIG_RESOURCE.getValue("letsgo"));
        searchButton.click();
    }

    /**
     * Select date in Flight Date
     *
     * @param localDate
     */
    public void selectDatePicker(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", LOCALE);
        String monthYear = localDate.format(formatter);
        date.set(monthYear, localDate.getDayOfMonth());
        date.click();
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
