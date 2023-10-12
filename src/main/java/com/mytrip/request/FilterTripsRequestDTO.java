package com.mytrip.request;

import com.mytrip.pojo.TripFilter;
import lombok.Data;

@Data
public class FilterTripsRequestDTO {
    private TripFilter tripFilter;
}
