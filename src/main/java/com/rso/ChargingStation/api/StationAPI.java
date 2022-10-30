package com.rso.ChargingStation.api;

import com.rso.ChargingStation.model.ChargingStation;
import com.rso.ChargingStation.model.Location;
import com.rso.ChargingStation.model.StationType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/station")
public class StationAPI {

    @GetMapping("/")
    public List<ChargingStation> getAllStations() {
        ChargingStation station1 = new ChargingStation(1L);
        ChargingStation station2 = new ChargingStation(2L);
        ChargingStation station3 = new ChargingStation(3L);
        return List.of(station1, station2, station3);
    }

    @GetMapping("/{id}")
    public ChargingStation getStationsById(@PathVariable("id") Long id) {
        return new ChargingStation(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteStationById(@PathVariable("id") Long id) {
        System.out.println("Deleted");
    }

    @GetMapping("/nearest")
    public ChargingStation getClosestStation(@RequestParam Double x, @RequestParam Double y){
        return new ChargingStation(100L,
                x,
                y,
                StationType.TYPE1,
                3,
                "Closest station");
    }

    @PostMapping(path="/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Long addStation(@RequestBody ChargingStation newChargingStation) {
        System.out.println(newChargingStation.toString());
        return 999L;
    }



}
