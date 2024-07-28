package com.project.ridebooking.RideBookingApplication.Strategy;

import com.project.ridebooking.RideBookingApplication.Dto.RideRequestDto;

public interface RideFareCalculationStrategy {

    Double calculateFare(RideRequestDto rideRequestDto);


}
