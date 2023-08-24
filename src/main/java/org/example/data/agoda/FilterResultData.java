package org.example.data.agoda;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class FilterResultData {

    private Integer minPrice;
    private Integer maxPrice;
    private boolean threeStarCheck;
}
