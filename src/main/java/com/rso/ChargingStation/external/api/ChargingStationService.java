package com.rso.ChargingStation.external.api;

import com.rso.ChargingStation.external.model.ChargingStationDict;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/v1/external")
public class ChargingStationService {

    @GetMapping
    public String getExternalData() {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://electric-vehicle-charging-station-and-point.p.rapidapi.com/us/elec.json?orderBy=%22city%22&equalTo=%22Sun%20Valley%22&print=%22pretty%22&limitToFirst=5"))
                .header("X-RapidAPI-Key", "cd7f29bfe3msh8913133c8980691p1f50b6jsnd655e1078453")
                .header("X-RapidAPI-Host", "electric-vehicle-charging-station-and-point.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
        return response.body();
    }
}
