package com.mytrip.service;

import com.mytrip.entity.FlightEntity;
import com.mytrip.request.CreateFlightRequestDTO;
import com.mytrip.response.CreateFlightResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class FlightServiceimpl implements FlightService {

    @PersistenceContext
    private EntityManager em;

    public CreateFlightResponseDTO createFlight(CreateFlightRequestDTO createFlightRequestDTO) {
        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setFlightNumber(createFlightRequestDTO.getFlight().getFlightNumber());
        flightEntity.setFlightFrom(createFlightRequestDTO.getFlight().getFlightFrom());
        flightEntity.setFlightTo(createFlightRequestDTO.getFlight().getFlightTo());
        flightEntity.setDepartureDate(createFlightRequestDTO.getFlight().getDepartureDate());
        flightEntity.setArrivalDate(createFlightRequestDTO.getFlight().getArrivalDate());
        em.persist(flightEntity);
        return new CreateFlightResponseDTO("Flight created successfully");
    }
}
