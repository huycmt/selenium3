package org.example.data.agoda;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterResultData {

    Integer minPrice;
    Integer maxPrice;
    boolean threeStarCheck;
}
