package com.rso.ChargingStation.model;

public record ChargingStation(Long id,
                              Location location,
                              String Description,
                              int numConectors,
                              StationType type) {
    public ChargingStation(Long id){
        this(id, new Location(0d,0d), "MOCK STATION",0, StationType.TYPE1);
    }
}
