package com.mytrip.pojo;

public enum TripReason {
    MEETING("Meeting"),
    TRAINING("Training"),
    PROJECT("Project"),
    WORKSHOP("Workshop"),
    EVENT("Event"),
    OTHER("Other");

    private String value;

    TripReason(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static TripReason getTripReasonByValue(String value) {
        for (TripReason tripReason : TripReason.values()) {
            if (value.equals(tripReason.getValue()))
                return tripReason;
        }
        return null;
    }
}
