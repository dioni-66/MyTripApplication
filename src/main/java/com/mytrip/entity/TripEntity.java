package com.mytrip.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "trip")
@NamedQuery(name = "TripEntity.deleteTrip", query = "DELETE FROM TripEntity t WHERE t.id =: id")
@NamedQuery(name = "TripEntity.loadTrip", query = "SELECT t FROM TripEntity t WHERE t.id =: id")
@NamedQuery(name = "TripEntity.updateFlightId", query = "UPDATE TripEntity t SET t.flightEntity.id = :flight_Id WHERE t.id = :id")
public class TripEntity {
    private int id;
    private String tripDescription;
    private String tripFrom;
    private String tripTo;
    private Date departureDate;
    private Date arrivalDate;
    private TripReasonEntity tripReason;
    private TripStatusEntity tripStatus;
    private FlightEntity flightEntity;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "trip_description")
    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    @Basic
    @Column(name = "trip_from")
    public String getTripFrom() {
        return tripFrom;
    }

    public void setTripFrom(String tripFrom) {
        this.tripFrom = tripFrom;
    }

    @Basic
    @Column(name = "trip_to")
    public String getTripTo() {
        return tripTo;
    }

    public void setTripTo(String tripTo) {
        this.tripTo = tripTo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_date")
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_date")
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripEntity that = (TripEntity) o;
        return id == that.id && Objects.equals(tripReason, that.tripReason) && Objects.equals(tripDescription, that.tripDescription) && Objects.equals(tripFrom, that.tripFrom) && Objects.equals(tripTo, that.tripTo) && Objects.equals(departureDate, that.departureDate) && Objects.equals(arrivalDate, that.arrivalDate) && Objects.equals(tripStatus, that.tripStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tripReason, tripDescription, tripFrom, tripTo, departureDate, arrivalDate, tripStatus);
    }

    @ManyToOne
    @JoinColumn(name = "reason_id", referencedColumnName = "id")
    public TripReasonEntity getTripReason() {
        return tripReason;
    }

    public void setTripReason(TripReasonEntity tripReason) {
        this.tripReason = tripReason;
    }

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    public TripStatusEntity getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatusEntity tripStatus) {
        this.tripStatus = tripStatus;
    }

    @OneToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    public FlightEntity getFlightEntity() {
        return flightEntity;
    }

    public void setFlightEntity(FlightEntity flightEntity) { this.flightEntity = flightEntity; }

}
