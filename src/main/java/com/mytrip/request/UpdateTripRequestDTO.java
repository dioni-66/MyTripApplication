package com.mytrip.request;

import com.mytrip.pojo.Trip;
import lombok.Data;

@Data
public class UpdateTripRequestDTO {
    private Trip trip;
}
