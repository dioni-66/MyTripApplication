package com.mytrip.service;

import com.mytrip.request.CreateFlightRequestDTO;
import com.mytrip.response.CreateFlightResponseDTO;

public interface FlightService {

    CreateFlightResponseDTO createFlight (CreateFlightRequestDTO createFlightRequestDTO);

}
