package org.example.data.agoda;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class FilterResultData {

    private Integer minPrice;
    private Integer maxPrice;
    private boolean threeStarCheck;
    private List<String> roomOffers;
    private List<String> facilities;
}
