package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Service.DistanceService;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistanceServiceOSRMImpl implements DistanceService {

    private final RestTemplate restTemplate;

    private final String OSRM_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";

    @Override
    public Double calculateDistance(Point src, Point dest) {

        String url = UriComponentsBuilder.fromHttpUrl(OSRM_BASE_URL)
                .path(String.format("%f,%f;%f,%f", src.getX(), src.getY(), dest.getX(), dest.getY()))
                .queryParam("overview", "false")
                .queryParam("steps", "false")
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray routes = jsonResponse.getJSONArray("routes");

        // Ensure there are routes in the response
        if (!routes.isEmpty()) {
            JSONObject firstRoute = routes.getJSONObject(0);
            return firstRoute.getDouble("distance");
        } else {
            throw new RuntimeException("No valid routes found in the response");
        }
    }

}
