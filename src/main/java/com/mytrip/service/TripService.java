package com.mytrip.service;

import com.mytrip.request.ApproveTripRequestDTO;
import com.mytrip.request.CreateTripRequestDTO;
import com.mytrip.request.DeleteTripRequestDTO;
import com.mytrip.request.FilterTripsRequestDTO;
import com.mytrip.response.*;

public interface TripService {

    CreateTripResponseDTO createTrip(CreateTripRequestDTO createTripRequestDTO);

    DeleteTripResponseDTO deleteTrip(DeleteTripRequestDTO deleteTripRequestDTO);

    LoadTripResponseDTO loadTrip(int tripId);

    SendForApprovalResponseDTO sendForApproval(int tripId);

    ApproveTripResponseDTO approveTrip(ApproveTripRequestDTO approveTripRequestDTO);

    UpdateTripResponseDTO addFlight(int id, int flightId);

    FilterTripsResponseDTO filterTrips(FilterTripsRequestDTO filterTripsRequestDTO);
}
