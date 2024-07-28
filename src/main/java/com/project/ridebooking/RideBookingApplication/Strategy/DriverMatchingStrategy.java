package com.project.ridebooking.RideBookingApplication.Strategy;

import com.project.ridebooking.RideBookingApplication.Dto.RideRequestDto;
import com.project.ridebooking.RideBookingApplication.Entity.Driver;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequestDto rideRequestDto);
}
