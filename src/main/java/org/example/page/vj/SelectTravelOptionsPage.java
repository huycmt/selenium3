package org.example.page.vj;

import org.example.element.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SelectTravelOptionsPage {

    public String getColorOfDepartureDate(LocalDate date) {
        String monthYear = date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
        if (date.getDayOfMonth() >= 15) {
            departureDateCell.set(monthYear, date.getDayOfMonth(), "first");
        } else {
            departureDateCell.set(monthYear, date.getDayOfMonth(), "last");
        }
        return departureDateCell.element().getCssValue("background-color");
    }

    public String getColorOfReturnDate(LocalDate date) {
        String monthYear = date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
        if (date.getDayOfMonth() >= 15) {
            returnDateCell.set(monthYear, date.getDayOfMonth(), "first");
        } else {
            returnDateCell.set(monthYear, date.getDayOfMonth(), "last");
        }
        return returnDateCell.element().getCssValue("background-color");
    }

    Element departureDateCell = new Element("xpath=((//p[.='%s']/../../../../../../../../..)[1]//p[.='%d'])[%s()]/..", true);
    Element returnDateCell = new Element("xpath=((//p[.='%s']/../../../../../../../../..)[2]//p[.='%d'])[%s()]/..", true);
}
