package com.mytrip.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "trip_status")
@NamedQuery(name = "TripStatusEntity.findByStatus", query = "SELECT t FROM TripStatusEntity t WHERE t.status =: status")
public class TripStatusEntity {
    private int id;
    private String status;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripStatusEntity that = (TripStatusEntity) o;
        return id == that.id && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }
}
