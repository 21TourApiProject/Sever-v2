package com.server.tourApiProject.hashTag;

public enum HashTagCategory {
    LOCATION(1),
    TRANSPORT(2),
    PEOPLE(3),
    THEME(4);

    private final int value;

    HashTagCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
