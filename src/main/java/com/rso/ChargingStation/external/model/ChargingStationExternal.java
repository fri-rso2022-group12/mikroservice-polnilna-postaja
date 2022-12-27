package com.rso.ChargingStation.external.model;

public class ChargingStationExternal {

    private String access_code;

    private String city;

    private String fuel_type_code;

    private String ev_network;

    public ChargingStationExternal() {
    }

    public String getAccess_code() {
        return access_code;
    }

    public void setAccess_code(String access_code) {
        this.access_code = access_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFuel_type_code() {
        return fuel_type_code;
    }

    public void setFuel_type_code(String fuel_type_code) {
        this.fuel_type_code = fuel_type_code;
    }

    public String getEv_network() {
        return ev_network;
    }

    public void setEv_network(String ev_network) {
        this.ev_network = ev_network;
    }
}
