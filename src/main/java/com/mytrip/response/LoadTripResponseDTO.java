package com.mytrip.response;

import com.mytrip.pojo.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadTripResponseDTO {
    private Trip trip;
}
