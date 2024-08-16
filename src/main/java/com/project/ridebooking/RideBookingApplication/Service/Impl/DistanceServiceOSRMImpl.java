package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Service.DistanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistanceServiceOSRMImpl implements DistanceService {

    private final String OSRM_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";

    @Override
    public Double calculateDistance(Point src, Point dest) {
        try {

            String response = RestClient.builder()
                    .baseUrl(OSRM_BASE_URL)
                    .build()
                    .get()
                    .uri(String.format("%f,%f;%f,%f", src.getX(), src.getY(), dest.getX(), dest.getY()))
                    .retrieve()
                    .body(String.class);

            JSONObject jsonResponse = new JSONObject(response);
            JSONArray routes = jsonResponse.getJSONArray("routes");

            if (!routes.isEmpty()) {
                JSONObject firstRoute = routes.getJSONObject(0);
                return firstRoute.getDouble("distance");
            } else {
                throw new RuntimeException("No valid routes found in the response");
            }
        } catch (Exception e){
            throw new RuntimeException("Error while getting distance from OSRM "+e.getMessage());
        }
    }


}
