package org.example.data.agoda;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class HotelData {

    String areaCity;
    Float star;
    Float price;
}
