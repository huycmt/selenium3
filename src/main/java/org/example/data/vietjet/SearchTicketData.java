package org.example.data.vietjet;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Builder
@ToString
public class SearchTicketData {

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
