package com.project.ridebooking.RideBookingApplication.Strategy;

import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;

public interface RideFareCalculationStrategy {

    Double calculateFare(RideRequest rideRequest);


}
