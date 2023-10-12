package com.mytrip.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Trip {

    private int id;

    private TripReason tripReason;

    private String tripDescription;

    private String tripFrom;

    private String tripTo;

    private Date departureDate;

    private Date arrivalDate;

    private TripStatus tripStatus;

}
