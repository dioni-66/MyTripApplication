package com.mytrip.service;

import com.mytrip.entity.FlightEntity;
import com.mytrip.entity.TripEntity;
import com.mytrip.entity.TripReasonEntity;
import com.mytrip.entity.TripStatusEntity;
import com.mytrip.pojo.Trip;
import com.mytrip.pojo.TripFilterCritera;
import com.mytrip.pojo.TripReason;
import com.mytrip.pojo.TripStatus;
import com.mytrip.request.ApproveTripRequestDTO;
import com.mytrip.request.CreateTripRequestDTO;
import com.mytrip.request.DeleteTripRequestDTO;
import com.mytrip.request.FilterTripsRequestDTO;
import com.mytrip.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    @PersistenceContext
    private EntityManager em;

    public CreateTripResponseDTO createTrip(CreateTripRequestDTO createTripRequestDTO) {

        if (createTripRequestDTO.getTripReason() == null) {
            return new CreateTripResponseDTO("Invalid trip reason used");
        }

        TripEntity tripEntity = new TripEntity();
        tripEntity.setTripFrom(createTripRequestDTO.getTripFrom());
        tripEntity.setTripTo(createTripRequestDTO.getTripTo());
        tripEntity.setTripDescription(createTripRequestDTO.getTripDescription());
        tripEntity.setDepartureDate(createTripRequestDTO.getDepartureDate());
        tripEntity.setArrivalDate(createTripRequestDTO.getArrivalDate());

        TypedQuery<TripReasonEntity> tripReasonTypedQuery = em.createNamedQuery("TripReasonEntity.findByReason", TripReasonEntity.class);
        tripReasonTypedQuery.setParameter("reason", createTripRequestDTO.getTripReason().getValue());
        final TripReasonEntity tripReasonEntity = tripReasonTypedQuery.getSingleResult();
        tripEntity.setTripReason(tripReasonEntity);
        //Initially all is set from user data except CREATED which is the first status of the trip
        TypedQuery<TripStatusEntity> tripStatusTypedQuery = em.createNamedQuery("TripStatusEntity.findByStatus", TripStatusEntity.class);
        tripStatusTypedQuery.setParameter("status", TripStatus.CREATED.getValue());
        final TripStatusEntity tripStatusEntity = tripStatusTypedQuery.getSingleResult();
        tripEntity.setTripStatus(tripStatusEntity);
        em.persist(tripEntity);
        return new CreateTripResponseDTO("Trip created successfully");
    }

    @Override
    public DeleteTripResponseDTO deleteTrip(DeleteTripRequestDTO deleteTripRequestDTO) {
        Query namedQuery = em.createNamedQuery("TripEntity.deleteTrip");
        namedQuery.setParameter("id", deleteTripRequestDTO.getTripId());
        namedQuery.executeUpdate();
        return new DeleteTripResponseDTO("Trip deleted successfully");
    }

    @Override
    public LoadTripResponseDTO loadTrip(int tripId) {
        TripEntity tripEntity = em.find(TripEntity.class, tripId);
        
        //convert to pojo and return the response
        Trip trip = convertTripEntityToPojo(tripEntity);

        return new LoadTripResponseDTO(trip);
    }

    @Override
    public SendForApprovalResponseDTO sendForApproval(int tripId) {
        TripEntity tripEntity = em.find(TripEntity.class, tripId);

        TypedQuery<TripStatusEntity> tripStatusTypedQuery = em.createNamedQuery("TripStatusEntity.findByStatus", TripStatusEntity.class);
        tripStatusTypedQuery.setParameter("status", TripStatus.WAITING_FOR_APPROVAL.getValue());
        final TripStatusEntity tripStatusEntity = tripStatusTypedQuery.getSingleResult();

        tripEntity.setTripStatus(tripStatusEntity);

        return new SendForApprovalResponseDTO("Trip sent for approval to admin");
    }

    @Override
    public ApproveTripResponseDTO approveTrip(ApproveTripRequestDTO approveTripRequestDTO) {
        if (!(approveTripRequestDTO.getTripStatus().equals(TripStatus.APPROVED) || approveTripRequestDTO.getTripStatus().equals(TripStatus.DISAPPROVED))) {
            return new ApproveTripResponseDTO("Invalid status sent for trip");
        }
        TripEntity tripEntity = em.find(TripEntity.class, approveTripRequestDTO.getTripId());

        TypedQuery<TripStatusEntity> tripStatusTypedQuery = em.createNamedQuery("TripStatusEntity.findByStatus", TripStatusEntity.class);
        tripStatusTypedQuery.setParameter("status", approveTripRequestDTO.getTripStatus().getValue());
        final TripStatusEntity tripStatusEntity = tripStatusTypedQuery.getSingleResult();

        tripEntity.setTripStatus(tripStatusEntity);

        return new ApproveTripResponseDTO(String.format("Trip status changed to %s", approveTripRequestDTO.getTripStatus().getValue()));
    }

    @Override
    public UpdateTripResponseDTO addFlight(int tripId, int flightId) {
        TripEntity tripEntity = em.find(TripEntity.class, tripId);
        if(tripEntity == null){
            return new UpdateTripResponseDTO("Trip id does not exist!");
        } else if (!tripEntity.getTripStatus().getStatus().equals(TripStatus.APPROVED.getValue())) {
            return new UpdateTripResponseDTO("Trip is not yet approved by Admin!");
        }
        FlightEntity flightEntity = em.find(FlightEntity.class, flightId);
        if (flightEntity == null) {
            return new UpdateTripResponseDTO("Flight id does not exist!");
        }
        tripEntity.setFlightEntity(flightEntity);
        return new UpdateTripResponseDTO("Flight added successfully");
    }

    @Override
    public FilterTripsResponseDTO filterTrips(FilterTripsRequestDTO filterTripsRequestDTO) {
        //TODO validate filter criteria and values

        TypedQuery<TripEntity> tripEntityTypedQuery = null;

        if (filterTripsRequestDTO.getTripFilter().getFilterCriteria().equals(TripFilterCritera.TRIP_STATUS.name())) {
            tripEntityTypedQuery = em.createNamedQuery("TripEntity.filterByStatus", TripEntity.class);
            tripEntityTypedQuery.setParameter("status", filterTripsRequestDTO.getTripFilter().getValue());
        } else if (filterTripsRequestDTO.getTripFilter().getFilterCriteria().equals(TripFilterCritera.TRIP_REASON.name())) {
            tripEntityTypedQuery = em.createNamedQuery("TripEntity.filterByReason", TripEntity.class);
            tripEntityTypedQuery.setParameter("reason", filterTripsRequestDTO.getTripFilter().getValue());
        }
        List<TripEntity> tripEntityList = tripEntityTypedQuery.getResultList();

        //convert to pojos and add the response
        FilterTripsResponseDTO filterTripsResponseDTO = new FilterTripsResponseDTO(new ArrayList<>());
        for (TripEntity tripEntity : tripEntityList) {
            Trip trip = convertTripEntityToPojo(tripEntity);
            filterTripsResponseDTO.getFilteredTrips().add(trip);
        }

        return filterTripsResponseDTO;
    }

    private Trip convertTripEntityToPojo(TripEntity tripEntity) {
        Trip trip = new Trip();
        trip.setId(tripEntity.getId());
        trip.setTripFrom(tripEntity.getTripFrom());
        trip.setTripTo(tripEntity.getTripTo());
        trip.setTripDescription(tripEntity.getTripDescription());
        trip.setDepartureDate(tripEntity.getDepartureDate());
        trip.setArrivalDate(tripEntity.getArrivalDate());
        trip.setTripReason(TripReason.getTripReasonByValue(tripEntity.getTripReason().getReason()));
        trip.setTripStatus(TripStatus.getTripStatusByValue(tripEntity.getTripStatus().getStatus()));
        return trip;
    }

}
