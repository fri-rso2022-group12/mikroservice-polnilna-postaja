package com.rso.ChargingStation.model;

import javax.persistence.*;

@Entity
@Table(name = "station")
public class ChargingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Double x;
    private Double y;
    private StationType type;
    private Integer numPorts;
    private String description;

    public ChargingStation() {
        this.id = 0L;
        this.x = 0d;
        this.y = 0d;
        this.type = StationType.TYPE1;
        this.numPorts = 0;
        this.description = "Mock object";
    }

    public ChargingStation(Long id, Double x, Double y, StationType type, Integer numPorts, String description) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.numPorts = numPorts;
        this.description = description;
    }

    public ChargingStation(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public StationType getType() {
        return type;
    }

    public void setType(StationType type) {
        this.type = type;
    }

    public Integer getNumPorts() {
        return numPorts;
    }

    public void setNumPorts(Integer numPorts) {
        this.numPorts = numPorts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ChargingStation{" +
                "Id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", type=" + type +
                ", numPorts=" + numPorts +
                ", description='" + description + '\'' +
                '}';
    }
}
