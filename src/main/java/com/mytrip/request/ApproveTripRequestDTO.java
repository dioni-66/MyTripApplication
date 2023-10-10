package com.mytrip.request;

import com.mytrip.pojo.TripStatus;
import lombok.Data;

@Data
public class ApproveTripRequestDTO {
    private int tripId;
    private TripStatus tripStatus;
}
