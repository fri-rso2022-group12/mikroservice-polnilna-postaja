package com.rso.ChargingStation;

import com.rso.ChargingStation.model.ChargingStation;
import org.springframework.stereotype.Service;

@Service
public class MockBrokenExternalService {

    public ChargingStation getStationByIdSlow(Long id, Long sleepDurationMil) {
        ChargingStation station = new ChargingStation();
        station.setId(999L);
        station.setDescription("Mock station by broken external api");
        try {
            Thread.sleep(sleepDurationMil);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return station;
    }
}
