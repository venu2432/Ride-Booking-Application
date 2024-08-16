package com.project.ridebooking.RideBookingApplication.Strategy.Impl;

import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;
import com.project.ridebooking.RideBookingApplication.Service.DistanceService;
import com.project.ridebooking.RideBookingApplication.Strategy.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final Double SURGE_FACTOR = 2.0;

    @Override
    public Double calculateFare(RideRequest rideRequest) {
        Double distance = distanceService.calculateDistance(rideRequest.getPickUpLocation(), rideRequest.getDropOffLocation());
        return distance/1000 * RIDE_FARE_MULTIPLIER * SURGE_FACTOR;
    }
}
