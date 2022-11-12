package com.rso.ChargingStation.repository;

import com.rso.ChargingStation.model.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ChargingStationRepository extends JpaRepository<ChargingStation,Long> {
    public ChargingStation getChargingStationById(Long id);

    public List<ChargingStation> getAllByIdBetween(Long start, Long stop);

    public void deleteById(Long id);

    @Query(value = "select top(1) * from station order by power((x - ?1),2) +  power((y - ?2),2)  asc", nativeQuery = true)
    public ChargingStation getNearestStation(Double x, Double y);

}
