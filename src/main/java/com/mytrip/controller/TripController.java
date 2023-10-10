package com.mytrip.controller;

import com.mytrip.request.ApproveTripRequestDTO;
import com.mytrip.request.CreateTripRequestDTO;
import com.mytrip.request.DeleteTripRequestDTO;
import com.mytrip.response.*;
import com.mytrip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/rest/trip")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/create")
    public ResponseEntity<CreateTripResponseDTO> createTrip(@RequestBody CreateTripRequestDTO createTripRequestDTO){
        CreateTripResponseDTO createTripResponseDTO = tripService.createTrip(createTripRequestDTO);
        return new ResponseEntity<>(createTripResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<DeleteTripResponseDTO> deleteTrip(@RequestBody DeleteTripRequestDTO deleteTripRequestDTO){
        DeleteTripResponseDTO deleteTripResponseDTO = tripService.deleteTrip(deleteTripRequestDTO);
        return new ResponseEntity<>(deleteTripResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<LoadTripResponseDTO> loadTrip(@PathVariable("id") int tripId){
        LoadTripResponseDTO loadTripResponseDTO = tripService.loadTrip(tripId);
        return new ResponseEntity<>(loadTripResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/send-for-approval/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<SendForApprovalResponseDTO> sendForApproval(@PathVariable("id") int tripId){
        SendForApprovalResponseDTO sendForApprovalResponseDTO = tripService.sendForApproval(tripId);
        return new ResponseEntity<>(sendForApprovalResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/approve-trip")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApproveTripResponseDTO> deleteTrip(@RequestBody ApproveTripRequestDTO approveTripRequestDTO){
        ApproveTripResponseDTO approveTripResponseDTO = tripService.approveTrip(approveTripRequestDTO);
        return new ResponseEntity<>(approveTripResponseDTO, HttpStatus.OK);
    }
}
