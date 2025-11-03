package com.food.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.food.request.GeoLocation;
import com.food.response.GoogleGeoResponse;

@Service
public class GeoDataIngestionService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${geo.googleApiKey}")
    private String googleApiKey;


    public GeoLocation geocodeAddress(String address) {
        String googleUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" 
                + address + "&key=" + googleApiKey;

        GoogleGeoResponse response = restTemplate.getForObject(googleUrl, GoogleGeoResponse.class);

        if (response != null && "OK".equals(response.getStatus()) 
            && response.getResults() != null && !response.getResults().isEmpty()) {

            double lat = response.getResults().get(0).getGeometry().getLocation().getLat();
            double lng = response.getResults().get(0).getGeometry().getLocation().getLng();
            return new GeoLocation(lat, lng);
        }

        throw new RuntimeException("Geocoding failed for address: " + address);
    }
}
