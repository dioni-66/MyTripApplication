package com.mytrip.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "trip_reason")
@NamedQuery(name = "TripReasonEntity.findByReason", query = "SELECT t FROM TripReasonEntity t WHERE t.reason =: reason")
public class TripReasonEntity {
    private int id;
    private String reason;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripReasonEntity that = (TripReasonEntity) o;
        return id == that.id && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reason);
    }
}
