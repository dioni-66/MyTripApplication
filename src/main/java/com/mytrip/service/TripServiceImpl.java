package com.mytrip.service;

import com.mytrip.entity.TripEntity;
import com.mytrip.entity.TripReasonEntity;
import com.mytrip.entity.TripStatusEntity;
import com.mytrip.pojo.Trip;
import com.mytrip.pojo.TripReason;
import com.mytrip.pojo.TripStatus;
import com.mytrip.request.ApproveTripRequestDTO;
import com.mytrip.request.CreateTripRequestDTO;
import com.mytrip.request.DeleteTripRequestDTO;
import com.mytrip.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    @PersistenceContext
    private EntityManager em;

    public CreateTripResponseDTO createTrip(CreateTripRequestDTO createTripRequestDTO) {

        if (createTripRequestDTO.getTrip().getTripReason() == null) {
            return new CreateTripResponseDTO("Invalid trip reason used");
        }

        TripEntity tripEntity = new TripEntity();
        tripEntity.setTripFrom(createTripRequestDTO.getTrip().getTripFrom());
        tripEntity.setTripTo(createTripRequestDTO.getTrip().getTripTo());
        tripEntity.setTripDescription(createTripRequestDTO.getTrip().getTripDescription());
        tripEntity.setDepartureDate(createTripRequestDTO.getTrip().getDepartureDate());
        tripEntity.setArrivalDate(createTripRequestDTO.getTrip().getArrivalDate());

        TypedQuery<TripReasonEntity> tripReasonTypedQuery = em.createNamedQuery("TripReasonEntity.findByReason", TripReasonEntity.class);
        tripReasonTypedQuery.setParameter("reason", createTripRequestDTO.getTrip().getTripReason().getValue());
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
        Trip trip = new Trip();
        trip.setTripFrom(tripEntity.getTripFrom());
        trip.setTripTo(tripEntity.getTripTo());
        trip.setTripDescription(tripEntity.getTripDescription());
        trip.setDepartureDate(tripEntity.getDepartureDate());
        trip.setArrivalDate(tripEntity.getArrivalDate());
        trip.setTripReason(TripReason.getTripReasonByValue(tripEntity.getTripReason().getReason()));
        trip.setTripStatus(TripStatus.getTripStatusByValue(tripEntity.getTripStatus().getStatus()));

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


}
