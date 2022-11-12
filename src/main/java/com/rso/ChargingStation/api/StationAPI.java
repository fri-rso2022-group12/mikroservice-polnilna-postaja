package com.rso.ChargingStation.api;

import com.rso.ChargingStation.model.ChargingStation;
import com.rso.ChargingStation.repository.ChargingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/v1/station")
public class StationAPI {

    @Autowired
    ChargingStationRepository chargingStationRepository;

    @GetMapping("/")
    public List<ChargingStation> getAllStations() {
        return chargingStationRepository.getAllByIdBetween(0l, 100l);
    }

    @GetMapping("/{id}")
    public ChargingStation getStationsById(@PathVariable("id") Long id) {
        ChargingStation res = chargingStationRepository.getChargingStationById(id);
        if (res == null){
            throw new EntityNotFoundException("No entity with id="+id.toString());
        }
        return res;
    }

    @GetMapping("/delete/{id}")
    public void deleteStationById(@PathVariable("id") Long id) {
        chargingStationRepository.deleteById(id);
    }

    @GetMapping("/nearest")
    public ChargingStation getClosestStation(@RequestParam Double x, @RequestParam Double y){
        return chargingStationRepository.getNearestStation(x, y);
    }

    @PostMapping(path="/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public void addStation(@RequestBody ChargingStation newChargingStation) {
        chargingStationRepository.save(newChargingStation);
    }

}
