package com.rso.ChargingStation.api;

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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Controller
public class StationGraphQlApi {

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

    @QueryMapping
    public Iterable<ChargingStation> getStations() {
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> chargingStationRepository.getAllByIdBetween(0l, 100l)).get();

    }

    @QueryMapping
    public ChargingStation getStationById(@Argument Long id) {
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
}
