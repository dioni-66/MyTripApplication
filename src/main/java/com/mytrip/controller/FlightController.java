package com.mytrip.controller;

import com.mytrip.response.CreateFlightResponseDTO;
import com.mytrip.request.CreateFlightRequestDTO;
import com.mytrip.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/rest/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @PostMapping("/create-flight")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CreateFlightResponseDTO> createFlight(@RequestBody CreateFlightRequestDTO createFlightRequestDTO){
    CreateFlightResponseDTO createFlightResponseDTO = flightService.createFlight(createFlightRequestDTO);
    return new ResponseEntity<>(createFlightResponseDTO, HttpStatus.OK);
    }

}
