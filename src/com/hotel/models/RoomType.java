package com.hotel.models;

public enum RoomType {
    SINGLE,
    DOUBLE,
    DELUXE,
    SUITE,
    PRESIDENTIAL;

    public double getBasePrice() {
        switch (this) {
            case SINGLE: return 1500;
            case DOUBLE: return 2500;
            case DELUXE: return 4000;
            case SUITE: return 7000;
            case PRESIDENTIAL: return 12000;
            default: return 1000;
        }
    }
}
