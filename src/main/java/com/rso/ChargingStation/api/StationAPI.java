package com.rso.ChargingStation.api;

import com.rso.ChargingStation.MockBrokenExternalService;
import com.rso.ChargingStation.model.ChargingStation;
import com.rso.ChargingStation.repository.ChargingStationRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("api/v1/station")
public class StationAPI {

    @Autowired
    ChargingStationRepository chargingStationRepository;
    @Autowired
    StationAsync stationAsync;

    @Autowired
    private CircuitBreaker countCircuitBreaker;

    private final Counter apiCallCounter = Metrics.counter("ApiCallCounter");
    private static final Logger logger = LoggerFactory.getLogger(StationAPI.class);

    private final TimeLimiter timeLimiter = TimeLimiter.of(TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofMillis(500)).build());

    @GetMapping("/")
    public List<ChargingStation> getAllStations() {
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> chargingStationRepository.getAllByIdBetween(0l, 100l)).get();
    }

    @GetMapping("/{id}")
    public ChargingStation getStationsById(@PathVariable("id") Long id) {
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> {
            ChargingStation res = null;
            try {
                res = stationAsync.getStationById(id).get(120L, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
            if (res == null){
                throw new EntityNotFoundException("No entity with id="+id.toString());
            }
            return res;
        }).get();
    }

    @GetMapping("/delete/{id}")
    public void deleteStationById(@PathVariable("id") Long id) {
        apiCallCounter.increment();
        logger.info("function call");
        chargingStationRepository.deleteById(id);
    }

    @GetMapping("/nearest")
    public ChargingStation getClosestStation(@RequestParam Double x, @RequestParam Double y){
        logger.info("function call");
        apiCallCounter.increment();
        return countCircuitBreaker.decorateSupplier(() -> chargingStationRepository.getNearestStation(x, y)).get();
    }

    @PostMapping(path="/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public void addStation(@RequestBody ChargingStation newChargingStation) {
        apiCallCounter.increment();
        logger.info("function call");
        chargingStationRepository.save(newChargingStation);
    }
}
