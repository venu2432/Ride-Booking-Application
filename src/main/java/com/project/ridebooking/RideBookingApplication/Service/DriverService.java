package com.project.ridebooking.RideBookingApplication.Service;

import com.project.ridebooking.RideBookingApplication.Dto.DriverDto;
import com.project.ridebooking.RideBookingApplication.Dto.RideDto;
import com.project.ridebooking.RideBookingApplication.Dto.RiderDto;
import com.project.ridebooking.RideBookingApplication.Entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String otp);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    RideDto acceptRide(Long rideRequestId);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverAvailability(Driver driver, Boolean availability);

    Driver createNewDriver(Driver driver);

}
