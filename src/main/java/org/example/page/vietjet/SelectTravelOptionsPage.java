package org.example.page.vietjet;

import org.example.element.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.example.utils.ConfigResourceBundle.CONFIG_RESOURCE;

public class SelectTravelOptionsPage {

    public String getColorOfDepartureDate(LocalDate date) {
        return getColorOfDate(date, departureDateCell);
    }

    public String getColorOfReturnDate(LocalDate date) {
        return getColorOfDate(date, returnDateCell);
    }

    private String getColorOfDate(LocalDate date, Element dateCell) {
        String monthYear = date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
        if (date.getDayOfMonth() >= 15) {
            dateCell.set(monthYear, date.getDayOfMonth(), "last()");
        } else {
            dateCell.set(monthYear, date.getDayOfMonth(), "1");
        }
        return dateCell.element().getCssValue("background-color");
    }

    public String getSelectingColor() {
        selectingColor.set(CONFIG_RESOURCE.getValue("selecting"));
        return selectingColor.element().getCssValue("background-color");
    }

    public String getFrom() {
        followingSpan.set(CONFIG_RESOURCE.getValue("from"));
        return followingSpan.getText();
    }

    public String getTo() {
        followingSpan.set(CONFIG_RESOURCE.getValue("to"));
        return followingSpan.getText();
    }

    public boolean isSelectTravelOptionsPageDisplayed() {
        span.set(CONFIG_RESOURCE.getValue("selectFare"));
        return span.isDisplayed();
    }

    public boolean isTheTicketPriceDisplayedIn(String currency) {
        followingSpan.set(CONFIG_RESOURCE.getValue("selectFare"));
        return followingSpan.getText().contains(currency);
    }

    public int getTotalPassenger() {
        spanContains.set(CONFIG_RESOURCE.getValue("adult"));
        return Integer.parseInt(spanContains.getText().split(" ")[0]);
    }

    private Element departureDateCell = new Element("xpath=((//p[.='%s']/../../../../../../../../..)[1]//p[.='%d'])[%s]/..", true);
    private Element returnDateCell = new Element("xpath=((//p[.='%s']/../../../../../../../../..)[2]//p[.='%d'])[%s]/..", true);
    private Element selectingColor = new Element("xpath=//h5[.='%s']/preceding-sibling::div[1]", true);
    private Element followingSpan = new Element("xpath=//span[.='%s']/following-sibling::span", true);
    private Element span = new Element("xpath=//span[.='%s']", true);
    private Element spanContains = new Element("xpath=//span[contains(.,'%s')]", true);
}
