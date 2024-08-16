package com.project.ridebooking.RideBookingApplication.Strategy.Impl;

import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import com.project.ridebooking.RideBookingApplication.Entity.RideRequest;
import com.project.ridebooking.RideBookingApplication.Repository.DriverRepository;
import com.project.ridebooking.RideBookingApplication.Strategy.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearByTopRatedDrivers(rideRequest.getPickUpLocation());
    }
}
