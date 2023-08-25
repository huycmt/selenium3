package org.example.data.agoda;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Builder
@ToString
public class SearchHotelData {

    boolean isDayUseStay;
    String place;
    LocalDate date;
    Occupancy occupancy;

    @Data
    @Builder
    public static class Occupancy {

        Integer rooms;
        Integer adult;
        Integer children;
    }
}
