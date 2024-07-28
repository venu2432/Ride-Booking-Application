package com.project.ridebooking.RideBookingApplication.Service;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.RideDto;
import com.project.ridebooking.RideBookingApplication.Dto.RiderDto;

import java.util.List;

public interface DriverService {

    RideDto cancelRide(Long rideId);

    RiderDto startRide(Long rideId);

    RiderDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    RiderDto acceptRide(Long rideId);

    DriverDto getMyProfile();

    List<RideDto> getAllMyRides();

}
