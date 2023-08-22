package org.example.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TicketData {

    private boolean isReturn;
    private String from;
    private String to;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String currency;
    private Passenger passenger;
    private boolean isFindLowestFare;

    @Data
    @Builder
    public static class Passenger {
        private Integer adultNumber;
        private Integer children;
        private Integer infants;
    }

}
