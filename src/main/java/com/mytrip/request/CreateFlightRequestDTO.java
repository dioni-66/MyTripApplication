package com.mytrip.request;

import com.mytrip.pojo.Flight;
import lombok.Data;

@Data
public class CreateFlightRequestDTO {
    private Flight flight;
}
