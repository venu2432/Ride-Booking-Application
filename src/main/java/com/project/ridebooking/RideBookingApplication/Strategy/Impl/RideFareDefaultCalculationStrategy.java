package com.project.ridebooking.RideBookingApplication.Strategy.Impl;

import com.project.ridebooking.RideBookingApplication.Dto.RideRequestDto;
import com.project.ridebooking.RideBookingApplication.Strategy.RideFareCalculationStrategy;

public class RideFareDefaultCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public Double calculateFare(RideRequestDto rideRequestDto) {
        return 0.0;
    }
}
