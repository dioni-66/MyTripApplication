package com.mytrip.response;

import com.mytrip.pojo.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilterTripsResponseDTO {
    private List<Trip> filteredTrips;
}
