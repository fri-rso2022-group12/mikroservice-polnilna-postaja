package com.rso.ChargingStation.api;

import com.rso.ChargingStation.model.ChargingStation;
import com.rso.ChargingStation.repository.ChargingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class StationAsync {
    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Async
    public CompletableFuture<ChargingStation> getStationById(Long id) {
        return CompletableFuture.completedFuture(chargingStationRepository.getChargingStationById(id));
    }
}
