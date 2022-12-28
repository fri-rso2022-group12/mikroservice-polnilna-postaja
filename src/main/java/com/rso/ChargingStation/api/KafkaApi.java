package com.rso.ChargingStation.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/kafka")
public class KafkaApi {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String message){
        kafkaTemplate.send("rso",String.format("sender: rso-tc-charging-station, \nmessage: '%s'",message));
    }


}
