package com.project.ridebooking.RideBookingApplication.Strategy.Impl;

import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;
import com.project.ridebooking.RideBookingApplication.Strategy.RideFareCalculationStrategy;

public class RideFareSurgePricingCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public Double calculateFare(RideRequest rideRequest) {
        return 0.0;
    }
}
