package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.request.LocationUpdate;
import com.food.service.RealtimeUpdateHandler;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final RealtimeUpdateHandler realtimeUpdateHandler;

    @Autowired
    public LocationController(RealtimeUpdateHandler realtimeUpdateHandler) {
        this.realtimeUpdateHandler = realtimeUpdateHandler;
    }

    // Endpoint for clients to POST location updates
    @PostMapping("/update")
    public ResponseEntity<String> updateLocation(@RequestBody LocationUpdate locationUpdate) {
        realtimeUpdateHandler.publishLocation(locationUpdate);
        return ResponseEntity.ok("Location updated for agent: " + locationUpdate.getAgentId());
    }
}
