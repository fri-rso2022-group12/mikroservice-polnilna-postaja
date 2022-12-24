package com.rso.ChargingStation.api;

import com.rso.ChargingStation.MockBrokenExternalService;
import com.rso.ChargingStation.model.ChargingStation;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.util.concurrent.*;

@RestController
@RequestMapping("api/v1/broken/station")
public class BrokenStationApi {
    @Autowired
    MockBrokenExternalService mockBrokenExternalService;

    @Autowired
    private CircuitBreaker countCircuitBreaker;

    private final Counter apiCallCounter = Metrics.counter("ApiCallCounter");
    private static final Logger logger = LoggerFactory.getLogger(StationAPI.class);


    @GetMapping("/")
    public ChargingStation getStationsById(@RequestParam("id") Long id, @RequestParam("delayMiliseconds") Long delayMiliseconds) {
        apiCallCounter.increment();
        logger.info("function call");
        return countCircuitBreaker.decorateSupplier(() -> {
            ChargingStation res = mockBrokenExternalService.getStationByIdSlow(id,delayMiliseconds);
            if( res == null ) {
                throw new EntityNotFoundException();
            }
            return res;
        }).get();
    }
}
