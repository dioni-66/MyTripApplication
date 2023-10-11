package com.mytrip.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;

@Data
public class Flight {
    @JsonIgnore
    private int id;
    private String flightNumber;
    private String flightFrom;
    private String flightTo;
    private Date departureDate;
    private Date arrivalDate;
}
