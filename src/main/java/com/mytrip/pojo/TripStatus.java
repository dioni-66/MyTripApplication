package com.mytrip.pojo;

public enum TripStatus {
    CREATED("CREATED"),
    WAITING_FOR_APPROVAL("WAITING FOR APPROVAL"),
    APPROVED("APPROVED"),
    DISAPPROVED("DISAPPROVED");

    private String value;

    TripStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TripStatus getTripStatusByValue(String value) {
        for (TripStatus tripStatus : TripStatus.values()) {
            if (value.equals(tripStatus.getValue()))
                return tripStatus;
        }
        return null;
    }
}
