package com.food.service;

import org.springframework.stereotype.Service;

import com.food.request.GeoLocation;

@Service
public class GeospatialLogicService {

    public double calculateDistance(GeoLocation loc1, GeoLocation loc2) {
        // Haversine formula to calculate distance in km between two lat/lng points
        double R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double lngDistance = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude()))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
