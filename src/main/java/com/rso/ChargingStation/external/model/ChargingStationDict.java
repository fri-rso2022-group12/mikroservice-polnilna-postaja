package com.rso.ChargingStation.external.model;

import java.util.HashMap;

public class ChargingStationDict {

    HashMap<Long, ChargingStationExternal> stations;

    public ChargingStationDict() {
    }

    public HashMap<Long, ChargingStationExternal> getStations() {
        return stations;
    }


    public void setStations(HashMap<Long, ChargingStationExternal> stations) {
        this.stations = stations;
    }
}
