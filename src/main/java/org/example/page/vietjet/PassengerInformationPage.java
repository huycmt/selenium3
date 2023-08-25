package org.example.page.vietjet;

import org.example.data.vietjet.TicketInfoData;
import org.example.element.Element;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.example.utils.ConfigResourceBundle.CONFIG_RESOURCE;
import static org.example.utils.Constants.TIME_FORMATTER;

public class PassengerInformationPage {

    /**
     * Is passenger information page displayed
     *
     * @return
     */
    public boolean isPassengerInformationPageDisplayed() {
        passengerInfo.set(CONFIG_RESOURCE.getValue("pasengerInfo"));
        return passengerInfo.isDisplayed();
    }

    /**
     * Get from place in reservation
     *
     * @param isDepartureFlight Is departure flight or not
     * @return
     */
    public String getFromInReservation(boolean isDepartureFlight) {
        String flight = isDepartureFlight ? CONFIG_RESOURCE.getValue("departureFlight") : CONFIG_RESOURCE.getValue("returnFlight");
        fromInReservation.set(flight);
        return fromInReservation.getText().split("\\(")[0].trim();
    }

    /**
     * Get to place in reservation
     *
     * @param isDepartureFlight Is departure flight or not
     * @return
     */
    public String getToInReservation(boolean isDepartureFlight) {
        String flight = isDepartureFlight ? CONFIG_RESOURCE.getValue("departureFlight") : CONFIG_RESOURCE.getValue("returnFlight");
        toInReservation.set(flight);
        return toInReservation.getText().split("\\(")[0].trim();
    }

    /**
     * Get ticket information in reservation
     *
     * @param isDepartureFlight Is departure flight or not
     * @return Ticket info
     */
    public TicketInfoData getTicketInfoOfReservation(boolean isDepartureFlight) {
        String flight = isDepartureFlight ? CONFIG_RESOURCE.getValue("departureFlight") : CONFIG_RESOURCE.getValue("returnFlight");
        flightInfo.set(flight);
        String[] info = flightInfo.getText().split("\\|");
        LocalDate date = LocalDate.parse(info[0].split(",")[1].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime fromTime = LocalTime.parse(info[1].split("-")[0].trim(), TIME_FORMATTER);
        LocalTime toTime = LocalTime.parse(info[1].split("-")[1].trim(), TIME_FORMATTER);
        String flightNo = info[2].trim();
        String ticketType = info[3].trim();
        return TicketInfoData.builder()
                .from(getFromInReservation(isDepartureFlight))
                .to(getToInReservation(isDepartureFlight))
                .flightDate(date)
                .fromTime(fromTime)
                .toTime(toTime)
                .flightNo(flightNo)
                .ticketType(ticketType)
                .build();
    }

    Element passengerInfo = new Element("xpath=//h3[.='%s']");
    Element fromInReservation = new Element("xpath=//div[p[.='%s']]/following-sibling::div[1]/div[1]/div[1]/h5[1]");
    Element toInReservation = new Element("xpath=//div[p[.='%s']]/following-sibling::div[1]/div[1]/div[1]/h5[2]");
    Element flightInfo = new Element("xpath=//div[p[.='%s']]/following-sibling::div[1]/div[1]/div[2]");
}
