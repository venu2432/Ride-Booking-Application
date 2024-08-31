package com.project.ridebooking.RideBookingApplication.Strategy.Impl;

import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;
import com.project.ridebooking.RideBookingApplication.Service.DistanceService;
import com.project.ridebooking.RideBookingApplication.Service.WeatherService;
import com.project.ridebooking.RideBookingApplication.Strategy.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideFareDefaultCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private final WeatherService weatherService;

    @Override
    public Double calculateFare(RideRequest rideRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<Double> distanceCalculated = distanceService.calculateDistance(rideRequest.getPickUpLocation(), rideRequest.getDropOffLocation());
        CompletableFuture<Double> weatherMultiplier = weatherService.getWeatherMultiplier(rideRequest.getPickUpLocation());
        Double distance = distanceCalculated.get();
        return distance/1000 * RIDE_FARE_MULTIPLIER * weatherMultiplier.get();
    }
}
