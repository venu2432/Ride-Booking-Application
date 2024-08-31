package com.project.ridebooking.RideBookingApplication.Strategy;

import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;

import java.util.concurrent.ExecutionException;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10.0;

    Double calculateFare(RideRequest rideRequest) throws ExecutionException, InterruptedException;

}
