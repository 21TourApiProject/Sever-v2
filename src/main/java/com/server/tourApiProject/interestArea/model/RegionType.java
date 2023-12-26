package com.server.tourApiProject.interestArea.model;

public enum RegionType {
    OBSERVATION(1), AREA(2);

    private final int value;

    RegionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
