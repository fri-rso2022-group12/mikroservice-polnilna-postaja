package com.rso.ChargingStation.listener;

import com.rso.ChargingStation.api.StationAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class KafkaListenerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListener.class);

    @KafkaListener(topics = "rso", groupId = "rso-tc-charging-station")
    public void listen(String message) {
        logger.info("group: 'rso-tc-charging-station', \n" + message);

    }
}
